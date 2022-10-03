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

import digger.model.Table
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
class TableController(
    private val datasourceService: DatasourceService,
    private val tableService: TableService,
    private val columnService: ColumnService,
    private val asciidoc: Asciidoc,
    private val text: Text
) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/datasources/{datasourceId}/tables/new")
    fun newTable(model: Model, @PathVariable datasourceId: Long?): String {
        val datasource = datasourceService.findById(datasourceId)
        model.addAttribute("datasource", datasource)
        model.addAttribute("table", Table())
        model.addAttribute("userGuideUrl", "$userGuideUrl#table-form")
        return "table_form"
    }

    @PostMapping("/datasources/{datasourceId}/tables")
    fun saveTable(@PathVariable datasourceId: Long?, @ModelAttribute table: Table): String {
        val datasource = datasourceService.findById(datasourceId)
        if (table.id != null) {
            val existingTable = tableService.findById(table.id)
            table.totalColumns = existingTable.totalColumns
        }
        table.datasource = datasource
        table.friendlyName = text.toFirstLetterUppercase(table.friendlyName)
        tableService.save(table)
        return "redirect:/datasources/{datasourceId}/tables/" + table.id
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}")
    fun openTable(model: Model, @PathVariable datasourceId: Long?, @PathVariable tableId: Long?): String {
        val datasource = datasourceService.findById(datasourceId) ?: return "redirect:/"
        model.addAttribute("datasource", datasource)
        val table = tableService.findById(tableId) ?: return "redirect:/datasources/{datasourceId}"
        model.addAttribute("table", table)
        model.addAttribute("progress", columnService.calculateProgress(table))
        model.addAttribute("userGuideUrl", "$userGuideUrl#table")
        table.documentation = asciidoc.toHtml(table.documentation)
        return "table"
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/edit")
    fun editTable(model: Model, @PathVariable datasourceId: Long?, @PathVariable tableId: Long?): String {
        model.addAttribute("datasource", datasourceService.findById(datasourceId))
        model.addAttribute("table", tableService.findById(tableId))
        model.addAttribute("userGuideUrl", "$userGuideUrl#table-form")
        return "table_form"
    }
}