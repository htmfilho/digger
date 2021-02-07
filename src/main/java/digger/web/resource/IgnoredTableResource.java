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

package digger.web.resource;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import digger.service.DatasourceService;
import digger.service.IgnoredTableService;
import digger.service.TableService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IgnoredTableResource {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final IgnoredTableService ignoredTableService;

    public IgnoredTableResource(DatasourceService datasourceService, TableService tableService, IgnoredTableService ignoredTableService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.ignoredTableService = ignoredTableService;
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/ignored", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<IgnoredTable> getIgnoredTables(@PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return ignoredTableService.findByDatasource(datasource);
    }
    
    @GetMapping(value = "/api/datasources/{datasourceId}/tables/ignorable", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getIgnorableTables(@PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        List<Table> undocumentedTables = tableService.listTables(datasource, null, null);
        return ignoredTableService.excludeIgnoredTables(datasource, undocumentedTables);
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/ignored/{ignoredTableId}")
    public void deleteIgnoredTable(@PathVariable Long datasourceId, @PathVariable Long ignoredTableId) {
        ignoredTableService.delete(ignoredTableId);
    }
}