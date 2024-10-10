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
package digger.web.controller

import digger.model.Column
import digger.service.ColumnService
import digger.service.DatasourceService
import digger.service.TableService
import digger.utils.Asciidoc
import digger.utils.Text
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ColumnController(
    private val datasourceService: DatasourceService,
    private val tableService: TableService,
    private val columnService: ColumnService,
    private val asciidoc: Asciidoc,
    private val text: Text
) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/new")
    fun newColumn(model: Model, @PathVariable datasourceId: Long?, @PathVariable tableId: Long?): String {
        model.addAttribute("datasource", datasourceService.findById(datasourceId))
        model.addAttribute("table", tableService.findById(tableId))
        model.addAttribute("column", Column())
        model.addAttribute("userGuideUrl", "$userGuideUrl#column-form")
        return "column_form"
    }

    @PostMapping("/datasources/{datasourceId}/tables/{tableId}/columns")
    fun saveColumn(
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?,
        @ModelAttribute column: Column
    ): String {
        val newOne = column.id == null
        val table = tableService.findById(tableId)
        column.table = table
        column.friendlyName = text.toFirstLetterUppercase(column.friendlyName)
        columnService.save(column)
        return if (newOne) "redirect:/datasources/{datasourceId}/tables/{tableId}" else "redirect:/datasources/{datasourceId}/tables/{tableId}/columns/" + column.id
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}")
    fun openColumn(
        model: Model,
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?,
        @PathVariable columnId: Long?
    ): String {
        val datasource = datasourceService.findById(datasourceId) ?: return "redirect:/"
        model.addAttribute("datasource", datasource)
        val table = tableService.findById(tableId) ?: return "redirect:/datasources/{datasourceId}"
        model.addAttribute("table", table)
        val column = columnService.findById(columnId) ?: return "redirect:/datasources/{datasourceId}/tables/{tableId}"
        column.documentation = asciidoc.toHtml(column.documentation)
        model.addAttribute("column", column)
        model.addAttribute("userGuideUrl", "$userGuideUrl#column")
        return "column"
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}/edit")
    fun editColumn(
        model: Model,
        @PathVariable datasourceId: Long?,
        @PathVariable tableId: Long?,
        @PathVariable columnId: Long?
    ): String {
        model.addAttribute("datasource", datasourceService.findById(datasourceId))
        model.addAttribute("table", tableService.findById(tableId))
        model.addAttribute("column", columnService.findById(columnId))
        model.addAttribute("userGuideUrl", "$userGuideUrl#column-form")
        return "column_form"
    }
}