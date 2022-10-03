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

import digger.model.StorageDto
import digger.service.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StorageResource(
    private val columnService: ColumnService,
    private val datasourceService: DatasourceService,
    private val ignoredTableService: IgnoredTableService,
    private val roleService: RoleService,
    private val tableService: TableService,
    private val userService: UserService
) {
    @get:GetMapping(
        value = ["/api/admin/storage"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    val storage: List<StorageDto>
        get() {
            val storageDtos: MutableList<StorageDto> = ArrayList(6)
            storageDtos.add(StorageDto("Column", columnService.countAll()))
            storageDtos.add(StorageDto("Datasource", datasourceService.countAll()))
            storageDtos.add(StorageDto("IgnoredTable", ignoredTableService.countAll()))
            storageDtos.add(StorageDto("Role", roleService.countAll()))
            storageDtos.add(StorageDto("Table", tableService.countAll()))
            storageDtos.add(StorageDto("User", userService.countAll()))
            return storageDtos
        }
}