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

import digger.exception.RoleAssignmentException
import digger.model.UserDto
import digger.model.enums.RoleKind
import digger.service.AdminService
import digger.service.RoleService
import digger.service.UserService
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import javax.servlet.http.HttpServletResponse

@Controller
class AdminController(
    private val userService: UserService,
    private val roleService: RoleService,
    private val adminService: AdminService,
    private val environment: Environment
) {
    @Value("\${user.guide.url}")
    private val userGuideUrl: String? = null
    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin")
        return "admin/index"
    }

    @GetMapping("/admin/users")
    fun listUsers(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-users")
        return "admin/users"
    }

    @GetMapping("/admin/storage")
    fun viewStorage(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-storage")
        addDatasourceAttributes(model)
        return "admin/storage"
    }

    @GetMapping("/admin/storage/backup")
    @Throws(IOException::class)
    fun downloadBackup(response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
        response.setHeader("Content-disposition", "attachment; filename=digger-backup.sql")
        val sqlStatements = adminService.exportToSql()
        val sqlBackup = StringBuilder()
        for (sqlStatement in sqlStatements) {
            sqlBackup.append(sqlStatement)
            sqlBackup.append("\n")
        }
        val `is`: InputStream = ByteArrayInputStream(sqlBackup.toString().toByteArray())
        IOUtils.copy(`is`, response.outputStream)
        response.flushBuffer()
    }

    @GetMapping("/admin/storage/restore")
    fun restoreBackup(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-storage")
        model.addAttribute("isDatabaseEmpty", adminService.isDatabaseEmpty)
        addDatasourceAttributes(model)
        return "admin/restore_form"
    }

    @PostMapping("/admin/storage/restore")
    fun restoreBackupFile(
        @RequestParam("backupFile") backupFile: MultipartFile,
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!adminService.isDatabaseEmpty) {
            model.addAttribute("error", "The database is not empty")
            return "/admin/storage/restore"
        }
        try {
            adminService.restoreBackup(backupFile.inputStream)
        } catch (e: IOException) {
            logger.error(e.message, e)
            model.addAttribute("error", e.message)
            return "/admin/storage/restore"
        }
        redirectAttributes.addFlashAttribute(
            "message",
            "The backup file " + backupFile.originalFilename + " was successfully restored."
        )
        return "redirect:/"
    }

    @GetMapping("/admin/environment")
    fun viewEnvironment(model: Model): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-environment")
        model.addAttribute("springProfilesActive", environment.getProperty("spring.profiles.active"))
        addDatasourceAttributes(model)
        model.addAttribute(
            "springJpaPropertiesHibernateJdbcLobNonContextualCreation",
            environment.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation")
        )
        model.addAttribute("loggingLevelRoot", environment.getProperty("logging.level.root"))
        model.addAttribute("loggingFileName", environment.getProperty("logging.file.name"))
        model.addAttribute("loggingLevelLiquibase", environment.getProperty("logging.level.liquibase"))
        model.addAttribute("springApplicationName", environment.getProperty("spring.application.name"))
        model.addAttribute("userGuideUrl", environment.getProperty("user.guide.url"))
        model.addAttribute("serverPort", environment.getProperty("server.port"))
        model.addAttribute("serverSessionTimeout", environment.getProperty("server.session.timeout"))
        model.addAttribute("springThymeleafCache", environment.getProperty("spring.thymeleaf.cache"))
        return "admin/environment"
    }

    private fun addDatasourceAttributes(model: Model) {
        model.addAttribute("springLiquibaseEnabled", environment.getProperty("spring.liquibase.enabled"))
        model.addAttribute(
            "springDatasourceDriverClassName",
            environment.getProperty("spring.datasource.driver-class-name")
        )
        model.addAttribute("springDatasourceUrl", environment.getProperty("spring.datasource.url"))
        model.addAttribute("springDatasourceUsername", environment.getProperty("spring.datasource.username"))
        model.addAttribute("springJpaHibernateDdlAuto", environment.getProperty("spring.jpa.hibernate.ddl-auto"))
        model.addAttribute("springJpaShowSql", environment.getProperty("spring.jpa.show-sql"))
    }

    @PostMapping("/admin/users")
    fun saveUser(@ModelAttribute user: UserDto): String {
        var existingUser = userService.findById(user.id)
        existingUser.username = user.username
        existingUser.firstName = user.firstName
        existingUser.lastName = user.lastName
        existingUser.enabled = if (user.enabled != null) user.enabled else false
        if (!user.password.trim { it <= ' ' }.isEmpty()) {
            existingUser = userService.changePassword(existingUser, user.password)
            logger.info("Changed the password of the user {}", existingUser.username)
        }
        val roleKind = RoleKind.valueOf(user.mainRole)
        userService.save(existingUser, roleKind)
        return "redirect:/admin/users/" + user.id
    }

    @GetMapping("/admin/users/{id}")
    fun openUser(model: Model, @PathVariable id: Long): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-user")
        val userDTO = createUserDto(id)
        model.addAttribute("user", userDTO)
        return "admin/user"
    }

    @GetMapping("/admin/users/{id}/edit")
    fun editUser(model: Model, @PathVariable id: Long): String {
        model.addAttribute("userGuideUrl", "$userGuideUrl#admin-user")
        val userDTO = createUserDto(id)
        model.addAttribute("user", userDTO)
        return "admin/user_form"
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(
        RoleAssignmentException::class
    )
    fun handleRoleAssignmentExceptions(rae: RoleAssignmentException): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "admin/user_form"
        modelAndView.addObject("userGuideUrl", "$userGuideUrl#admin-user")
        modelAndView.addObject("error", rae.message)
        modelAndView.addObject("user", createUserDto(rae.user.id))
        return modelAndView
    }

    private fun createUserDto(id: Long): UserDto {
        val user = userService.findById(id)
        val role = roleService.findByUsername(user.username)
        return UserDto(
            user.id, user.firstName, user.lastName, user.username, user.enabled,
            role.authority
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AdminController::class.java)
    }
}