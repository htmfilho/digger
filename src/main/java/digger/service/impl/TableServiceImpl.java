/*
 * Digger
 * Copyright (C) 2019-2020 Hildeberto Mendonca
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

import digger.model.Datasource;
import digger.model.Table;
import digger.repository.TableRepository;
import digger.service.DatasourceService;
import digger.service.IgnoredTableService;
import digger.service.TableService;
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
public class TableServiceImpl implements TableService {
    private static final Logger logger = LoggerFactory.getLogger(TableService.class);

    private static final int TABLE_NAME = 3;
    private static final int TABLE_TYPE = 4;

    private final DatasourceService datasourceService;
    private final IgnoredTableService ignoredTableService;
    private final TableRepository tableRepository;

    public TableServiceImpl(DatasourceService datasourceService, IgnoredTableService ignoredTableService, TableRepository tableRepository) {
        this.datasourceService = datasourceService;
        this.ignoredTableService = ignoredTableService;
        this.tableRepository = tableRepository;
    }

    public List<Table> listTables(final Datasource datasource, final String key, final Table except) {
        List<Table> tables = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(datasource)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, key == null ? null : key + "%", null);
            while (resultSet.next()) {
                Table table = new Table();
                table.setName(resultSet.getString(TABLE_NAME));
                table.setType(resultSet.getString(TABLE_TYPE));
                tables.add(table);
            }
            resultSet.close();
            Collections.sort(tables);
            datasourceService.updateTotalTables(datasource, tables.size());
        } catch (SQLException se) {
            logger.warn("Connection not available.");
        }
        tables = excludeDocumentedTables(datasource, tables, except);
        tables = ignoredTableService.excludeIgnoredTables(datasource, tables);
        return tables;
    }

    public List<Table> excludeDocumentedTables(final Datasource datasource, List<Table> tables, final Table except) {
        List<Table> documentedTables = findByDatasource(datasource);
        if (except != null) {
            documentedTables.remove(except);
        }
        tables.removeAll(documentedTables);
        return tables;
    }

    public Table getTable(final Datasource datasource, final Table table) {
        return tableRepository.findById(table.getId());
    }

    public void save(Table table) {
        tableRepository.save(table);
        logger.info("Saved table {}", table.getName());
    }

    public void delete(Long tableId) {
        tableRepository.deleteById(tableId);
        logger.info("Deleted table with id {}", tableId);
    }

    public void updateTotalColumns(Table table, final Integer totalColumns) {
        table.setTotalColumns(totalColumns);
        save(table);
        logger.info("Updated the number of columns of the table {} to {}", table.getName(), totalColumns);
    }

    public Table findById(final Long id) {
        return tableRepository.findById(id);
    }

    public List<Table> findByDatasource(final Datasource datasource) {
        return tableRepository.findByDatasourceOrderByNameAsc(datasource);
    }

    public Integer countDocumentedTables(final Datasource datasource) {
        List<Table> documentedTables = findByDatasource(datasource);
        return documentedTables.size();
    }

    public Integer calculateProgress(final Datasource datasource) {
        if(datasource.getTotalTables() != null && datasource.getTotalTables() > 0) {
            int totalIgnoredTables = ignoredTableService.getTotalIgnoredTable(datasource);
            return Math.round(countDocumentedTables(datasource) / ((datasource.getTotalTables() - totalIgnoredTables) * 1.0f) * 100);
        }
        else
            return 0;
    }
}