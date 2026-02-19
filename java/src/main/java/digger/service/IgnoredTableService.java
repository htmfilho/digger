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

package digger.service;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

import java.util.List;

public interface IgnoredTableService {

    IgnoredTable findById(Long id);

    Long countAll();

    int getTotalIgnoredTable(Datasource datasource);

    List<IgnoredTable> findByDatasource(Datasource datasource);
    List<Table> excludeIgnoredTables(Datasource datasource, List<Table> tables);
    List<String> exportToSql();

    void save(List<IgnoredTable> ignoredTables);
    void delete(Long ignoredTableId);

    List<Table> toTableList(List<IgnoredTable> ignoredTables);
}