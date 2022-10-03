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

import digger.model.IgnoredTable
import digger.service.DatasourceService
import digger.service.IgnoredTableService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IgnoredTableController(
    private val datasourceService: DatasourceService,
    private val ignoredTableService: IgnoredTableService
) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/datasources/{datasourceId}/tables/ignored/new")
    fun newIgnoredTable(model: Model, @PathVariable datasourceId: Long?): String {
        val datasource = datasourceService.findById(datasourceId)
        model.addAttribute("datasource", datasource)
        model.addAttribute("ignoredTable", IgnoredTable())
        model.addAttribute("userGuideUrl", "$userGuideUrl#ignored-table-form")
        return "ignored_table_form"
    }

    @PostMapping("/datasources/{datasourceId}/tables/ignored")
    fun saveIgnoredTable(
        @PathVariable datasourceId: Long?,
        @RequestParam(value = "ignored", required = false) checkedValues: Array<String?>
    ): String {
        val datasource = datasourceService.findById(datasourceId)
        val ignoredTables: MutableList<IgnoredTable> = ArrayList(checkedValues.size)
        for (tableName in checkedValues) {
            val ignoredTable = IgnoredTable(tableName, datasource)
            ignoredTables.add(ignoredTable)
        }
        ignoredTableService.save(ignoredTables)
        return "redirect:/datasources/{datasourceId}?tab=ignored"
    }
}