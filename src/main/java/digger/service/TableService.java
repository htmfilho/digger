package digger.service;

import digger.model.Datasource;
import digger.model.Table;
import digger.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TableService {

    private static final Logger log = LoggerFactory.getLogger(TableService.class.getName());

    private static final int TABLE_NAME = 3;
    private static final int TABLE_TYPE = 4;

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableRepository tableRepository;

    public List<Table> listTables(Datasource datasource, String key, Table except) {
        List<Table> tables = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(datasource)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, key == null ? key : key + "%", null);
            while (resultSet.next()) {
                Table table = new Table();
                table.setName(resultSet.getString(TABLE_NAME));
                table.setType(resultSet.getString(TABLE_TYPE));
                tables.add(table);                
            }
            Collections.sort(tables);
            datasourceService.updateTotalTables(datasource, tables.size());
        } catch (SQLException se) {
            log.warn("Connection not available.");
        }
        return excludeDocumentedTables(datasource, tables, except);
    }

    public List<Table> excludeDocumentedTables(Datasource datasource, List<Table> tables, Table except) {
        List<Table> documentedTables = findByDatasource(datasource);
        if (except != null) {
            documentedTables.remove(except);
        }
        tables.removeAll(documentedTables);
        return tables;
    }

    public Table getTable(Datasource datasource, Table table) {
        List<Table> tables = listTables(datasource, table.getName(), null);
        if (tables.isEmpty()) {
            return table;
        } else {
            return tables.get(0);
        }
    }

    public void save(Table table) {
        tableRepository.save(table);
    }

    public void updateTotalColumns(Table table, Integer totalColumns) {
        table.setTotalColumns(totalColumns);
        save(table);
    }

    public Table findById(Long id) {
        return tableRepository.findById(id);
    }

    public List<Table> findByDatasource(Datasource datasource) {
        return tableRepository.findByDatasourceOrderByNameAsc(datasource);
    }

    public Integer countDocumentedTables(Datasource datasource) {
        List<Table> documentedTables = findByDatasource(datasource);
        return documentedTables.size();
    }

    public Integer calculateProgress(Datasource datasource) {
        if(datasource.getTotalTables() != null && datasource.getTotalTables() > 0)
            return Math.round(countDocumentedTables(datasource) / (datasource.getTotalTables() * 1.0f) * 100);
        else
            return 0;
    }
}
