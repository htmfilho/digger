/*
 * Digger
 * Copyright (C) 2019-2021 Hildeberto Mendonca
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

import digger.exception.RoleAssignmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import digger.model.Role;
import digger.model.User;
import digger.model.UserDTO;
import digger.model.enums.RoleKind;
import digger.service.RoleService;
import digger.service.UserService;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final RoleService roleService;
    private final Environment environment;

    public AdminController(UserService userService, RoleService roleService, Environment environment) {
        this.userService = userService;
        this.roleService = roleService;
        this.environment = environment;
    }

    @Value("${user.guide.url}")
    private String userGuideUrl;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin");
        return "admin/index";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-users");
        return "admin/users";
    }

    @GetMapping("/admin/environment")
    public String viewEnvironment(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-environment");

        model.addAttribute("springProfilesActive", environment.getProperty("spring.profiles.active"));

        model.addAttribute("springLiquibaseEnabled", environment.getProperty("spring.liquibase.enabled"));
        model.addAttribute("springDatasourceDriverClassName", environment.getProperty("spring.datasource.driver-class-name"));
        model.addAttribute("springDatasourceUrl", environment.getProperty("spring.datasource.url"));
        model.addAttribute("springDatasourceUsername", environment.getProperty("spring.datasource.username"));
        model.addAttribute("springJpaHibernateDdlAuto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        model.addAttribute("springJpaShowSql", environment.getProperty("spring.jpa.show-sql"));
        model.addAttribute("springJpaPropertiesHibernateJdbcLobNonContextualCreation", environment.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));

        model.addAttribute("loggingLevelRoot", environment.getProperty("logging.level.root"));
        model.addAttribute("loggingFileName", environment.getProperty("logging.file.name"));
        model.addAttribute("loggingLevelLiquibase", environment.getProperty("logging.level.liquibase"));

        model.addAttribute("springApplicationName", environment.getProperty("spring.application.name"));
        model.addAttribute("userGuideUrl", environment.getProperty("user.guide.url"));

        model.addAttribute("serverPort", environment.getProperty("server.port"));
        model.addAttribute("serverSessionTimeout", environment.getProperty("server.session.timeout"));

        model.addAttribute("springThymeleafCache", environment.getProperty("spring.thymeleaf.cache"));

        return  "admin/environment";
    }

    @PostMapping("/admin/users")
    public String saveUser( @ModelAttribute UserDTO user) {
        User existingUser = userService.findById(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : false);

        if (!user.getPassword().trim().isEmpty()) {
            existingUser = userService.changePassword(existingUser, user.getPassword());
            logger.info("Changed the password of the user {}", existingUser.getUsername());
        }

        RoleKind roleKind = RoleKind.valueOf(user.getMainRole());

        userService.save(existingUser, roleKind);

        return "redirect:/admin/users/"+ user.getId();
    }

    @GetMapping("/admin/users/{id}")
    public String openUser(Model model, @PathVariable Long id) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-user");
        UserDTO userDTO = createUserDTO(id);
        model.addAttribute("user", userDTO);
        return "admin/user";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable Long id) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-user");
        UserDTO userDTO = createUserDTO(id);
        model.addAttribute("user", userDTO);
        return "admin/user_form";
    }

    private UserDTO createUserDTO(Long id) {
        User user = userService.findById(id);
        Role role = roleService.findByUsername(user.getUsername());
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEnabled(),
                           role.getAuthority());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RoleAssignmentException.class)
    public ModelAndView handleRoleAssignmentExceptions(RoleAssignmentException rae) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/user_form");
        modelAndView.addObject("userGuideUrl", userGuideUrl + "#admin-user");
        modelAndView.addObject("error", rae.getMessage());
        modelAndView.addObject("user", createUserDTO(rae.getUser().getId()));
        return modelAndView;
    }
}