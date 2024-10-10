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
package digger.web.resource

import digger.model.Datasource
import digger.service.DatasourceService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DatasourceResource(private val datasourceService: DatasourceService) {
    @get:GetMapping(
        value = ["/api/datasources"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    val datasources: List<Datasource>
        get() = datasourceService.findAll()

    @GetMapping(value = ["/api/datasources/{datasourceId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDatasource(@PathVariable datasourceId: Long?): Datasource {
        return datasourceService.findById(datasourceId)
    }

    @DeleteMapping("/api/datasources/{datasourceId}")
    fun deleteDatasource(@PathVariable datasourceId: Long?) {
        datasourceService.delete(datasourceId)
    }
}