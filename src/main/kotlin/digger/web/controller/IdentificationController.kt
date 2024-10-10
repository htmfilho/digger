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

import digger.model.User
import digger.model.UserDto
import digger.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class IdentificationController(private val userService: UserService) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/login")
    fun login(model: Model): String {
        if (userService.thereIsNoUser()) return "redirect:/signup"
        model.addAttribute("userGuideUrl", "$userGuideUrl#login")
        return "login"
    }

    @GetMapping("/signup")
    fun signUp(model: Model): String {
        model.addAttribute("user", User())
        model.addAttribute("thereIsNoUser", userService.thereIsNoUser())
        model.addAttribute("userGuideUrl", "$userGuideUrl#signup")
        return "signup"
    }

    @PostMapping("/users/new")
    fun newUser(model: Model, @ModelAttribute user: User): String {
        try {
            if (userService.thereIsNoUser()) userService.registerAdmin(user) else userService.registerReader(user)
        } catch (re: RuntimeException) {
            logger.error(re.message, re)
            model.addAttribute("user", user)
            model.addAttribute("thereIsNoUser", userService.thereIsNoUser())
            model.addAttribute("emailError", "A user with email '" + user.username + "' already exists.")
            return "signup"
        }
        return "redirect:/"
    }

    @GetMapping("/users/profile")
    fun userProfile(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#profile")
        return "profile"
    }

    @GetMapping("/users/password")
    fun changePassword(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#change-password")
        return "change_password"
    }

    @PostMapping("/users/password")
    fun changePassword(model: Model, principal: Principal, @ModelAttribute user: UserDto): String {
        var existingUser = userService.findByUsername(principal.name)
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        if (passwordEncoder.matches(user.currentPassword, existingUser.password)) {
            existingUser = userService.changePassword(existingUser, user.password)
            logger.info("Changed the password of the user {}", existingUser.username)
            userService.save(existingUser)
            return "redirect:/users/profile"
        }
        model.addAttribute("passwordMatchError", "Current password doesn't match.")
        return "change_password"
    }

    companion object {
        private val logger = LoggerFactory.getLogger(IdentificationController::class.java)
    }
}