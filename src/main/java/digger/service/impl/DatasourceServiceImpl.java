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
import digger.repository.DatasourceRepository;
import digger.service.DatasourceService;
import digger.utils.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatasourceServiceImpl implements DatasourceService {
    private static final Logger logger = LoggerFactory.getLogger(DatasourceService.class);

    private final DatasourceRepository datasourceRepository;

    public DatasourceServiceImpl(DatasourceRepository datasourceRepository) {
        this.datasourceRepository = datasourceRepository;
    }

    public Long countAll() {
        return datasourceRepository.count();
    }

    public List<Datasource> findAll() {
        return datasourceRepository.findAllByOrderByName();
    }

    public Datasource findById(final Long id) {
        return datasourceRepository.findById(id);
    }

    public void save(Datasource datasource) {
        try {
            datasourceRepository.save(datasource);
        } catch (DataIntegrityViolationException dive) {
            BigDecimal seq = datasourceRepository.getSequenceNextVal();
            logger.warn("Datasource sequence out of sync. Incrementing sequence to: {}", seq);
            save(datasource);
        }
        logger.info("Saved datasource {}", datasource.getName());
    }

    public void delete(Long datasourceId) {
        datasourceRepository.deleteById(datasourceId);
        logger.info("Deleted datasource sith id {}", datasourceId);
    }

    public void updateTotalTables(Datasource datasource, final Integer totalTables) {
        datasource.setTotalTables(totalTables);
        save(datasource);
        logger.info("Updated the total number of tables of the datasource {} to {}", datasource.getName(), totalTables);
    }

    public Boolean testConnection(final Datasource datasource) throws SQLException {
        return (getConnection(datasource) != null);
    }

    public Connection getConnection(final Datasource datasource) throws SQLException {
        if (datasource == null) return null;

        try {
            Class.forName(datasource.getDriver());
            logger.info("Driver '{}' available.", datasource.getDriver());
            Connection connection = DriverManager.getConnection(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
            logger.info("Connection to '{}' was successful.", datasource.getName());
            return connection;
        } catch (ClassNotFoundException cnfe) {
            logger.warn("Driver '{}' not found.", datasource.getDriver());
        } catch (SQLException se) {
            logger.error("Error connecting to the datasource '{}'. {}", datasource.getName(), se.getMessage());
            throw se;
        }
        return null;
    }

    public List<String> exportToSql() {
        List<String> sqlStatements = new ArrayList<>();
        sqlStatements.add("delete from datasource;");
        List<Datasource> allDatasources = datasourceRepository.findAll();
        for (Datasource datasource: allDatasources) {
            String sqlStatement = "insert into datasource (id, name, description, driver, url, username, password, total_tables) values (" +
                    SqlHelper.fieldToSql(datasource.getId(), ",") +
                    SqlHelper.fieldToSql(datasource.getName(), ",") +
                    SqlHelper.fieldToSql(datasource.getDescription(), ",") +
                    SqlHelper.fieldToSql(datasource.getDriver(), ",") +
                    SqlHelper.fieldToSql(datasource.getUrl(), ",") +
                    SqlHelper.fieldToSql(datasource.getUsername(), ",") +
                    SqlHelper.fieldToSql(datasource.getPassword(), ",") +
                    SqlHelper.fieldToSql(datasource.getTotalTables(), ");");
            sqlStatements.add(sqlStatement);
        }
        sqlStatements.add("\n");
        return sqlStatements;
    }
}