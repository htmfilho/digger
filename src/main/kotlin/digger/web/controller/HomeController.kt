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

package digger.web.controller

import digger.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val userService: UserService) {

    @Value("\${user.guide.url}")
    lateinit var userGuideUrl: String

    @GetMapping("/")
    fun index(model: Model): String {
        if(userService.thereIsNoUser()) {
            return "redirect:/signup"
        }

        model.addAttribute("userGuideUrl", userGuideUrl)

        return "index"
    }

    @GetMapping("/error")
    fun error(model: Model): String {
        model.addAttribute("userGuideUrl", userGuideUrl)
        return "error"
    }
}