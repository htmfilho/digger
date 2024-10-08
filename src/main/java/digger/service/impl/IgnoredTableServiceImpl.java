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

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import digger.repository.IgnoredTableRepository;
import digger.service.IgnoredTableService;
import digger.utils.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IgnoredTableServiceImpl implements IgnoredTableService {
    private static final Logger logger = LoggerFactory.getLogger(IgnoredTableService.class);

    private final IgnoredTableRepository ignoredTableRepository;

    public IgnoredTableServiceImpl(IgnoredTableRepository ignoredTableRepository) {
        this.ignoredTableRepository = ignoredTableRepository;
    }

    public void save(List<IgnoredTable> ignoredTables) {
        try {
            for (IgnoredTable ignoredTable: ignoredTables) {
                ignoredTableRepository.save(ignoredTable);
                logger.info("Saved ignored table {}", ignoredTable.getName());
            }
        } catch (DataIntegrityViolationException dive) {
            BigDecimal seq = ignoredTableRepository.getSequenceNextVal();
            logger.warn("IgnoredTable sequence out of sync. Incrementing sequence to: {}", seq);
            save(ignoredTables);
        }
    }

    public IgnoredTable findById(final Long id) {
        return ignoredTableRepository.findById(id);
    }

    public List<IgnoredTable> findByDatasource(final Datasource datasource) {
        return ignoredTableRepository.findByDatasourceOrderByNameAsc(datasource);
    }

    public Long countAll() {
        return ignoredTableRepository.count();
    }

    public int getTotalIgnoredTable(final Datasource datasource) {
        return findByDatasource(datasource).size();
    }

    public List<Table> excludeIgnoredTables(final Datasource datasource, List<Table> tables) {
        List<IgnoredTable> existingIgnoredTables = findByDatasource(datasource);
        tables.removeAll(toTableList(existingIgnoredTables));
        return tables;
    }

    public void delete(final Long ignoredTableId) {
        ignoredTableRepository.deleteById(ignoredTableId);
        logger.info("Deleted ignored table with id {}", ignoredTableId);
    }

    public List<Table> toTableList(final List<IgnoredTable> ignoredTables) {
        List<Table> tables = new ArrayList<>(ignoredTables.size());
        for (IgnoredTable ignoredTable: ignoredTables) {
            tables.add(ignoredTable.toTable());
        }
        return tables;
    }

    public List<String> exportToSql() {
        List<String> sqlStatements = new ArrayList<>();
        sqlStatements.add("delete from ignored_table;");
        List<IgnoredTable> allIgnoredTables = ignoredTableRepository.findAll();
        for (IgnoredTable ignoredTable: allIgnoredTables) {
            String sqlStatement = "insert into ignored_table (id, datasource, name) values (" +
                    SqlHelper.fieldToSql(ignoredTable.getId(), ",") +
                    SqlHelper.fieldToSql(ignoredTable.getDatasource().getId(), ",") +
                    SqlHelper.fieldToSql(ignoredTable.getName(), ");");
            sqlStatements.add(sqlStatement);
        }
        sqlStatements.add("\n");
        return sqlStatements;
    }
}