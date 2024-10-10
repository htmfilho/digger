/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */
package digger.service.impl;

import digger.model.Column;
import digger.model.ColumnComparator;
import digger.model.Datasource;
import digger.model.Table;
import digger.repository.ColumnRepository;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import digger.utils.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {
    private static final Logger logger = LoggerFactory.getLogger(ColumnService.class);

    private static final int COLUMN_NAME = 4;
    private static final int COLUMN_TYPE = 6;
    private static final int COLUMN_SIZE = 7;
    private static final int COLUMN_NULLABLE = 11;
    private static final int COLUMN_DEFAULT = 13;

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnRepository columnRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository, DatasourceService datasourceService, TableService tableService) {
        this.columnRepository = columnRepository;
        this.datasourceService = datasourceService;
        this.tableService = tableService;
    }

    public List<Column> listUndocumentedColumns(final Datasource datasource, final Table table, final String key, final Column except) {
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

            // Side effect to improve efficiency: updates the number of columns at the moment the number of columns is known.
            tableService.updateTotalColumns(table, columns.size());
        } catch (SQLException se) {
            logger.error("Error: {}", se.getMessage());
        }
        return excludeDocumentedColumns(table, columns, except);
    }

    public List<Column> listPrimaryKeys(final Table table) {
        List<Column> primaryKeys = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(table.getDatasource())) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getPrimaryKeys(connection.getCatalog(), null, table.getName());
            while (resultSet.next()) {
                Column primaryKey = new Column();
                primaryKey.setName(resultSet.getString(COLUMN_NAME));
                primaryKey.setType(resultSet.getString(COLUMN_TYPE));
                primaryKeys.add(primaryKey);
                logger.info("name: {}", primaryKey.getName());
            }
            resultSet.close();
        } catch (SQLException se) {
            logger.error("Error: {}", se.getMessage());
        }
        return primaryKeys;
    }

    public boolean isPrimaryKey(final Column column) {
        List<Column> primaryKeys = listPrimaryKeys(column.getTable());
        for(Column primaryKey : primaryKeys) {
            if(column.equals(primaryKey)) {
                return true;
            }
        }
        return false;
    }

    public List<Column> excludeDocumentedColumns(final Table table, List<Column> columns, final Column except) {
        List<Column> documentedColumns = this.findByTable(table);

        // Remove except column from documentedColumns so it doesn't get removed from the list of undocumented columns.
        if (except != null) {
            documentedColumns.remove(except);
        }

        columns.removeAll(documentedColumns);
        return columns;
    }

    public Column getColumn(final Datasource datasource, final Table table, final Column column) {
        List<Column> columns = listUndocumentedColumns(datasource, table, column.getName(), null);
        if (columns.isEmpty()) {
            return column;
        } else {
            return columns.get(0);
        }
    }

    public void save(Column column) {
        column.setPrimaryKey(isPrimaryKey(column));
        try {
            columnRepository.save(column);
        } catch (DataIntegrityViolationException dive) {
            BigDecimal seq = columnRepository.getSequenceNextVal();
            logger.warn("Column sequence out of sync. Incrementing sequence to: {}", seq);
            save(column);
        }
        logger.info("Saved the column {}", column.getName());
    }

    public void delete(final Long columnId) {
        columnRepository.deleteById(columnId);
        logger.info("Deleted the column with id {}", columnId);
    }

    public Long countAll() {
        return columnRepository.count();
    }

    public Column findById(final Long id) {
        return columnRepository.findById(id);
    }

    public List<Column> findByTable(final Table table) {
        List<Column> columns = columnRepository.findByTable(table);
        columns.sort(new ColumnComparator());
        return columns;
    }

    public List<Column> findByForeignKey(final Column foreignKey) {
        return columnRepository.findByForeignKey(foreignKey);
    }

    public List<Column> findByForeignKeyIn(List<Column> columns) {
        return columnRepository.findByForeignKeyIn(columns);
    }

    public Integer countDocumentedColumns(final Table table) {
        List<Column> documentedColumns = findByTable(table);
        return documentedColumns.size();
    }

    public Integer calculateProgress(final Table table) {
        if(table.getTotalColumns() != null && table.getTotalColumns() > 0)
            return Math.round(countDocumentedColumns(table) / (table.getTotalColumns() * 1.0f) * 100);
        else
            return 0;
    }

    public List<String> exportToSql() {
        List<String> sqlStatements = new ArrayList<>();
        sqlStatements.add("delete from table_column;");
        List<Column> allColumns = columnRepository.findAllByOrderByForeignKeyAsc();
        for (Column column: allColumns) {
            String sqlStatement = "insert into table_column (id, database_table, name, friendly_name, type, size, nullable, default_value, documentation, foreign_key, primary_key, sensitive) values (" +
                    SqlHelper.fieldToSql(column.getId(), ",") +
                    SqlHelper.fieldToSql(column.getTable().getId(), ",") +
                    SqlHelper.fieldToSql(column.getName(), ",") +
                    SqlHelper.fieldToSql(column.getFriendlyName(), ",") +
                    SqlHelper.fieldToSql(column.getType(), ",") +
                    SqlHelper.fieldToSql(column.getSize(), ",") +
                    SqlHelper.fieldToSql(column.getNullable(), ",") +
                    SqlHelper.fieldToSql(column.getDefaultValue(), ",") +
                    SqlHelper.fieldToSql(column.getDocumentation(), ",") +
                    SqlHelper.fieldToSql(column.getForeignKey(), ",") +
                    SqlHelper.fieldToSql(column.getPrimaryKey(), ",") +
                    SqlHelper.fieldToSql(column.getSensitive(), ");");
            sqlStatements.add(sqlStatement);
        }
        sqlStatements.add("\n");
        return sqlStatements;
    }
}
