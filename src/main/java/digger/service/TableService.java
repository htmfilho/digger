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
import digger.model.Table;

import java.util.List;

public interface TableService {

    Table findById(Long id);
    Table getTable(Datasource datasource, Table table);

    List<Table> listTables(Datasource datasource, String key, Table except);
    List<Table> excludeDocumentedTables(Datasource datasource, List<Table> tables, Table except);
    List<Table> findByDatasource(Datasource datasource);

    Long countAll();
    Integer countDocumentedTables(Datasource datasource);

    Integer calculateProgress(Datasource datasource);

    void save(Table table);
    void delete(Long tableId);
    void updateTotalColumns(Table table, Integer totalColumns);
}
