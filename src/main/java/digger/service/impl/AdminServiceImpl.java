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

package digger.service.impl;

import digger.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final ColumnService columnService;
    private final DatasourceService datasourceService;
    private final IgnoredTableService ignoredTableService;
    private final RoleService roleService;
    private final TableService tableService;
    private final UserService userService;

    public AdminServiceImpl(ColumnService columnService, DatasourceService datasourceService,
                             IgnoredTableService ignoredTableService, RoleService roleService,
                             TableService tableService, UserService userService) {
        this.columnService = columnService;
        this.datasourceService = datasourceService;
        this.ignoredTableService = ignoredTableService;
        this.roleService = roleService;
        this.tableService = tableService;
        this.userService = userService;
    }

    public List<String> exportToSql() {
        // This sequence represents the dependencies between database tables.
        List<String> sqlStatements = userService.exportToSql();
        sqlStatements.addAll(roleService.exportToSql());
        sqlStatements.addAll(datasourceService.exportToSql());
        sqlStatements.addAll(tableService.exportToSql());
        sqlStatements.addAll(ignoredTableService.exportToSql());
        sqlStatements.addAll(columnService.exportToSql());
        return sqlStatements;
    }
}