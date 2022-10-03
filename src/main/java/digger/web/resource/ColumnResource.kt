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

import digger.model.Column
import digger.model.Table
import digger.service.ColumnService
import digger.service.DatasourceService
import digger.service.TableService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class ColumnResource(
    private val datasourceService: DatasourceService,
    private val tableService: TableService,
    private val columnService: ColumnService
) {
    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableId}/columns"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getColumns(
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?,
        @RequestParam(value = "key", defaultValue = "") key: String?,
        @RequestParam(value = "except", defaultValue = "") except: Long?
    ): List<Column> {
        val datasource = datasourceService.findById(datasourceId)
        val table = tableService.findById(tableId)
        val exceptColumn = columnService.findById(except)
        return columnService.listUndocumentedColumns(datasource, table, key, exceptColumn)
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableId}/columns/documented"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getDocumentedColumns(
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?
    ): List<Column> {
        val table = tableService.findById(tableId)
        return columnService.findByTable(table)
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableName}/columns/{columnName}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getColumn(
        @PathVariable datasourceId: Long?,
        @PathVariable tableName: String?,
        @PathVariable columnName: String?
    ): Column {
        val datasource = datasourceService.findById(datasourceId)
        val table = Table(tableName)
        val column = Column(columnName)
        return columnService.getColumn(datasource, table, column)
    }

    @GetMapping(
        value = ["/api/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}/foreignkeys"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getForeignKeys(
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?,
        @PathVariable columnId: Long?
    ): List<Column> {
        return columnService.findByForeignKey(Column(columnId))
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}")
    fun deleteDatasource(@PathVariable columnId: Long?) {
        columnService.delete(columnId)
    }
}