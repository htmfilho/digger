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
import digger.service.DatasourceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DatasourceResource {

    private final DatasourceService datasourceService;

    public DatasourceResource(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @GetMapping(value = "/api/datasources", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Datasource> getDatasources() {
        return this.datasourceService.findAll();
    }

    @GetMapping(value = "/api/datasources/{datasourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Datasource getDatasource(@PathVariable Long datasourceId) {
        return this.datasourceService.findById(datasourceId);
    }

    @DeleteMapping("/api/datasources/{datasourceId}")
    public void deleteDatasource(@PathVariable Long datasourceId) {
        datasourceService.delete(datasourceId);
    }
}