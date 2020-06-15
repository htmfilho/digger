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

import digger.exception.RoleAssignmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @PostMapping("/admin/users")
    public String saveUser( @ModelAttribute UserDTO user) {
        User existingUser = userService.findById(user.getId());
        existingUser.setUsername(user.getUsername());
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
        return new UserDTO(user.getId(), user.getUsername(), user.getEnabled(), role.getAuthority());
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