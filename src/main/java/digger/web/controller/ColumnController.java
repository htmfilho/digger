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

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import digger.utils.Asciidoc;
import digger.utils.Text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ColumnController {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnService columnService;
    private final Asciidoc asciidoc;
    private final Text text;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public ColumnController(DatasourceService datasourceService, TableService tableService, ColumnService columnService, Asciidoc asciidoc, Text text) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.columnService = columnService;
        this.asciidoc = asciidoc;
        this.text = text;
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/new")
    public String newColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        model.addAttribute("column", new Column());
        model.addAttribute("userGuideUrl", userGuideUrl + "#column-form");
        return "column_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables/{tableId}/columns")
    public String saveColumn(@PathVariable Long datasourceId, @PathVariable Long tableId, @ModelAttribute Column column) {
        boolean newOne = column.getId() == null;

        Table table = tableService.findById(tableId);

        column.setTable(table);
        column.setFriendlyName(text.toFirstLetterUppercase(column.getFriendlyName()));
        columnService.save(column);

        if (newOne)
            return "redirect:/datasources/{datasourceId}/tables/{tableId}";
        else
            return "redirect:/datasources/{datasourceId}/tables/{tableId}/columns/" + column.getId();
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}")
    public String openColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId, @PathVariable Long columnId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        if(datasource == null) return "redirect:/";
        model.addAttribute("datasource", datasource);

        Table table = tableService.findById(tableId);
        if(table == null) return "redirect:/datasources/{datasourceId}";
        model.addAttribute("table", table);

        Column column = columnService.findById(columnId);
        if(column == null) return "redirect:/datasources/{datasourceId}/tables/{tableId}";
        
        column.setDocumentation(asciidoc.toHtml(column.getDocumentation()));

        model.addAttribute("column", column);
        model.addAttribute("userGuideUrl", userGuideUrl + "#column");

        return "column";
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}/edit")
    public String editColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId, @PathVariable Long columnId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        model.addAttribute("column", columnService.findById(columnId));
        model.addAttribute("userGuideUrl", userGuideUrl + "#column-form");
        return "column_form";
    }
}