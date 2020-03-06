package digger.service;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.repository.ColumnRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ColumnService {

    private static final Logger log = LoggerFactory.getLogger(ColumnService.class.getName());

    private static final int COLUMN_NAME = 4;
    private static final int COLUMN_TYPE = 6;
    private static final int COLUMN_SIZE = 7;
    private static final int COLUMN_NULLABLE = 11;
    private static final int COLUMN_DEFAULT = 13;

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository, DatasourceService datasourceService, TableService tableService) {
        this.columnRepository = columnRepository;
        this.datasourceService = datasourceService;
        this.tableService = tableService;
    }

    public List<Column> listUndocumentedColumns(Datasource datasource, Table table, String key, Column except) {
        List<Column> columns = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(datasource)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(connection.getCatalog(), null, table.getName(), key + "%");
            while (resultSet.next()) {
                Column column = new Column();
                column.setName(resultSet.getString(COLUMN_NAME));
                column.setType(resultSet.getString(COLUMN_TYPE));
                column.setSize(resultSet.getInt(COLUMN_SIZE));
                column.setNullable(resultSet.getBoolean(COLUMN_NULLABLE));
                column.setDefaultValue(resultSet.getString(COLUMN_DEFAULT));
                columns.add(column);
            }
            resultSet.close();
            Collections.sort(columns);
            tableService.updateTotalColumns(table, columns.size());
        } catch (SQLException se) {
            log.warn("Error: {}", se.getMessage());
        }
        return excludeDocumentedColumns(table, columns, except);
    }

    public List<Column> excludeDocumentedColumns(Table table, List<Column> columns, Column except) {
        List<Column> documentedColumns = this.findByTable(table);
        // Remove except column from documentedColumns so it doesn't get removed from the list of undocumented columns.
        if (except != null) {
            documentedColumns.remove(except);
        }

        columns.removeAll(documentedColumns);
        return columns;
    }

    public Column getColumn(Datasource datasource, Table table, Column column) {
        List<Column> columns = listUndocumentedColumns(datasource, table, column.getName(), null);
        if (columns.isEmpty()) {
            return column;
        } else {
            return columns.get(0);
        }
    }

    public void save(Column column) {
        columnRepository.save(column);
    }

    public Column findById(Long id) {
        if(id != null)
            return columnRepository.findById(id);
        else
            return null;
    }

    public List<Column> findByTable(Table table) {
        return columnRepository.findByTableOrderByNameAsc(table);
    }

    public List<Column> findByForeignKey(Column foreignKey) {
        return columnRepository.findByForeignKey(foreignKey);
    }

    public Integer countDocumentedColumns(Table table) {
        List<Column> documentedColumns = findByTable(table);
        return documentedColumns.size();
    }

    public Integer calculateProgress(Table table) {
        if(table.getTotalColumns() != null && table.getTotalColumns() > 0)
            return Math.round(countDocumentedColumns(table) / (table.getTotalColumns() * 1.0f) * 100);
        else
            return 0;
    }
}
