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

import digger.model.Datasource
import digger.model.enums.SupportedDriverClass
import digger.service.DatasourceService
import digger.service.TableService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.sql.SQLException

@Controller
class DatasourceController(private val datasourceService: DatasourceService, private val tableService: TableService) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/datasources/new")
    fun newDatasource(model: Model): String {
        model.addAttribute("datasource", Datasource())
        model.addAttribute("supportedDriverClasses", SupportedDriverClass.getListSupportedDriverClasses())
        model.addAttribute("userGuideUrl", "$userGuideUrl#datasource-form")
        return "datasource_form"
    }

    @PostMapping("/datasources")
    fun saveDatasource(@ModelAttribute datasource: Datasource): String {
        if (datasource.id != null) {
            val existingDatasource = datasourceService.findById(datasource.id)
            datasource.totalTables = existingDatasource.totalTables
        }
        datasourceService.save(datasource)
        return "index"
    }

    @GetMapping("/datasources/{datasourceId}")
    fun openDatasource(model: Model, @PathVariable datasourceId: Long?): String {
        val datasource = datasourceService.findById(datasourceId)
        try {
            datasource.status = datasourceService.testConnection(datasource)
        } catch (e: SQLException) {
            model.addAttribute("exception", e.message)
        }
        model.addAttribute("datasource", datasource)
        model.addAttribute("progress", tableService.calculateProgress(datasource))
        model.addAttribute("userGuideUrl", "$userGuideUrl#datasource")
        return "datasource"
    }

    @GetMapping("/datasources/{datasourceId}/edit")
    fun editDatasource(model: Model, @PathVariable datasourceId: Long?): String {
        model.addAttribute("datasource", datasourceService.findById(datasourceId))
        model.addAttribute("supportedDriverClasses", SupportedDriverClass.getListSupportedDriverClasses())
        model.addAttribute("userGuideUrl", "$userGuideUrl#datasource-form")
        return "datasource_form"
    }
}