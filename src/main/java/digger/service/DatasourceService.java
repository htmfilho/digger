/*
 * Digger
 * Copyright (C) 2019-2021 Hildeberto Mendonca
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

package digger.service;

import digger.model.Datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatasourceService {

    Datasource findById(Long id);

    Boolean testConnection(Datasource datasource) throws SQLException;

    Connection getConnection(Datasource datasource) throws SQLException;

    List<Datasource> findAll();

    void save(Datasource datasource);
    void delete(Long datasourceId);
    void updateTotalTables(Datasource datasource, Integer totalTables);
}