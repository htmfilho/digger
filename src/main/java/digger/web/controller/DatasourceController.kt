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

package digger.web.controller;

import digger.model.Datasource;
import digger.model.enums.SupportedDriverClass;
import digger.service.DatasourceService;
import digger.service.TableService;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DatasourceController {

    private final DatasourceService datasourceService;
    private final TableService tableService;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public DatasourceController(DatasourceService datasourceService, TableService tableService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
    }

    @GetMapping("/datasources/new")
    public String newDatasource(Model model) {
        model.addAttribute("datasource", new Datasource());
        model.addAttribute("supportedDriverClasses", SupportedDriverClass.getListSupportedDriverClasses());
        model.addAttribute("userGuideUrl", userGuideUrl + "#datasource-form");
        return "datasource_form";
    }

    @PostMapping("/datasources")
    public String saveDatasource(@ModelAttribute Datasource datasource) {
        if (datasource.getId() != null) {
            Datasource existingDatasource = datasourceService.findById(datasource.getId());
            datasource.setTotalTables(existingDatasource.getTotalTables());
        }
        datasourceService.save(datasource);

        return "index";
    }

    @GetMapping("/datasources/{datasourceId}")
    public String openDatasource(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        try {
            datasource.setStatus(datasourceService.testConnection(datasource));
        } catch (SQLException e) {
            model.addAttribute("exception", e.getMessage());
        }
        model.addAttribute("datasource", datasource);
        model.addAttribute("progress", tableService.calculateProgress(datasource));
        model.addAttribute("userGuideUrl", userGuideUrl + "#datasource");
        return "datasource";
    }

    @GetMapping("/datasources/{datasourceId}/edit")
    public String editDatasource(Model model, @PathVariable Long datasourceId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("supportedDriverClasses", SupportedDriverClass.getListSupportedDriverClasses());
        model.addAttribute("userGuideUrl", userGuideUrl + "#datasource-form");
        return "datasource_form";
    }
}