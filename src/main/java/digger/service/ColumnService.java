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

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;

import java.util.List;

public interface ColumnService {

    Column findById(Long id);
    Column getColumn(Datasource datasource, Table table, Column column);
        
    List<Column> listUndocumentedColumns(Datasource datasource, Table table, String key, Column except);
    List<Column> excludeDocumentedColumns(Table table, List<Column> columns, Column except);
    List<Column> findByTable(Table table);
    List<Column> findByForeignKey(Column foreignKey);
    List<Column> findByForeignKeyIn(List<Column> columns);

    Integer countDocumentedColumns(Table table);
    Integer calculateProgress(Table table);

    void save(Column column);
    void delete(Long columnId);
}