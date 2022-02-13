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

package digger.web.resource;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
public class TableResource {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnService columnService;

    public TableResource(DatasourceService datasourceService, TableService tableService, ColumnService columnService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.columnService = columnService;
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getTables(@PathVariable Long datasourceId, 
                                 @RequestParam(value = "key", defaultValue = "") String key,
                                 @RequestParam(value = "except", defaultValue = "") Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.listTables(datasource, key, new Table(tableId));
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/documented", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getDocumentedTables(@PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.findByDatasource(datasource);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Table getTable(@PathVariable Long datasourceId, @PathVariable Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableId);
        return tableService.getTable(datasource, table);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}/foreignkeys",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Table> getForeignKeys(@PathVariable Long datasourceId, @PathVariable Long tableId) {
        Table table = new Table(tableId);
        List<Column> columns = columnService.findByTable(table);
        List<Column> foreignKeys = columnService.findByForeignKeyIn(columns);
        Set<Table> tables = new TreeSet<>();
        for(Column column: foreignKeys) {
            tables.add(column.getTable());
        }
        return tables;
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/{tableId}")
    public void deleteTable(@PathVariable Long datasourceId, @PathVariable Long tableId) {
        tableService.delete(tableId);
    }
}
