/*
 * Digger
 * Copyright (C) 2019-2020 Hildeberto Mendonca
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

package digger.web.controller;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.service.DatasourceService;
import digger.service.IgnoredTableService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IgnoredTableController {

    private final DatasourceService datasourceService;
    private final IgnoredTableService ignoredTableService;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public IgnoredTableController(DatasourceService datasourceService, IgnoredTableService ignoredTableService) {
        this.datasourceService = datasourceService;
        this.ignoredTableService = ignoredTableService;
    }

    @GetMapping("/datasources/{datasourceId}/tables/ignored/new")
    public String newIgnoredTable(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        model.addAttribute("datasource", datasource);
        model.addAttribute("ignoredTable", new IgnoredTable());
        model.addAttribute("userGuideUrl", userGuideUrl + "#ignored-table-form");
        return "ignored_table_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables/ignored")
    public String saveIgnoredTable(@PathVariable Long datasourceId, @RequestParam(value = "ignored", required = false) String[] checkedValues) {
        Datasource datasource = datasourceService.findById(datasourceId);
        List<IgnoredTable> ignoredTables = new ArrayList<>(checkedValues.length);
        for (String tableName : checkedValues) {
            IgnoredTable ignoredTable = new IgnoredTable(tableName, datasource);
            ignoredTables.add(ignoredTable);
        }
        ignoredTableService.save(ignoredTables);

        return "redirect:/datasources/{datasourceId}?tab=ignored";
    }
}