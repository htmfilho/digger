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

import digger.model.StorageDTO;
import digger.service.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StorageResource {

    private final ColumnService columnService;
    private final DatasourceService datasourceService;
    private final IgnoredTableService ignoredTableService;
    private final RoleService roleService;
    private final TableService tableService;
    private final UserService userService;

    public StorageResource(ColumnService columnService, DatasourceService datasourceService,
                           IgnoredTableService ignoredTableService, RoleService roleService, TableService tableService,
                           UserService userService) {
        this.columnService = columnService;
        this.datasourceService = datasourceService;
        this.ignoredTableService = ignoredTableService;
        this.roleService = roleService;
        this.tableService = tableService;
        this.userService = userService;
    }

    @GetMapping(value = "/api/admin/storage", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StorageDTO> getStorage() {
        List<StorageDTO> storageDTOs = new ArrayList<>(6);

        storageDTOs.add(new StorageDTO("Column", this.columnService.countAll()));
        storageDTOs.add(new StorageDTO("Datasource", this.datasourceService.countAll()));
        storageDTOs.add(new StorageDTO("IgnoredTable", this.ignoredTableService.countAll()));
        storageDTOs.add(new StorageDTO("Role", this.roleService.countAll()));
        storageDTOs.add(new StorageDTO("Table", this.tableService.countAll()));
        storageDTOs.add(new StorageDTO("User", this.userService.countAll()));

        return storageDTOs;
    }
}