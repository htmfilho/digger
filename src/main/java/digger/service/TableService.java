package digger.service;

import digger.model.Datasource;
import digger.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    private static final Logger log = LoggerFactory.getLogger(TableService.class.getName());

    private static final int TABLE_NAME = 3;
    private static final int TABLE_TYPE = 4;

    @Autowired
    private DatasourceService datasourceService;

    public List<Table> listTables(Datasource datasource, String key) {
        List<Table> tables = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(datasource)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, key + "%", null);
            while (resultSet.next()) {
                Table table = new Table();
                table.setName(resultSet.getString(TABLE_NAME));
                table.setType(resultSet.getString(TABLE_TYPE));
                tables.add(table);
            }
        } catch (SQLException se) {
            log.warn("Connection not available.");
        }
        return tables;
    }

    public Table getTable(Datasource datasource, Table table) {
        List<Table> tables = listTables(datasource, table.getName());
        if (tables.isEmpty()) {
            return table;
        } else {
            return tables.get(0);
        }
    }
}
