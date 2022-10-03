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

import digger.model.Table
import digger.service.ColumnService
import digger.service.DatasourceService
import digger.service.TableService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TableResource(
    private val datasourceService: DatasourceService,
    private val tableService: TableService,
    private val columnService: ColumnService
) {
    @GetMapping(value = ["/api/datasources/{datasourceId}/tables"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTables(
        @PathVariable datasourceId: Long?,
        @RequestParam(value = "key", defaultValue = "") key: String?,
        @RequestParam(value = "except", defaultValue = "") tableId: Long?
    ): List<Table> {
        val datasource = datasourceService.findById(datasourceId)
        return tableService.listTables(datasource, key, Table(tableId))
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/documented"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getDocumentedTables(@PathVariable datasourceId: Long?): List<Table> {
        val datasource = datasourceService.findById(datasourceId)
        return tableService.findByDatasource(datasource)
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTable(@PathVariable datasourceId: Long?, @PathVariable tableId: Long?): Table {
        val datasource = datasourceService.findById(datasourceId)
        val table = Table(tableId)
        return tableService.getTable(datasource, table)
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableId}/foreignkeys"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getForeignKeys(@PathVariable datasourceId: Long?, @PathVariable tableId: Long?): Set<Table> {
        val table = Table(tableId)
        val columns = columnService.findByTable(table)
        val foreignKeys = columnService.findByForeignKeyIn(columns)
        val tables: MutableSet<Table> = TreeSet()
        for (column in foreignKeys) {
            tables.add(column.table)
        }
        return tables
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/{tableId}")
    fun deleteTable(@PathVariable datasourceId: Long?, @PathVariable tableId: Long?) {
        tableService.delete(tableId)
    }
}