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

package digger.web.resource;

import digger.model.Role;
import digger.model.User;
import digger.model.UserDTO;
import digger.service.RoleService;
import digger.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserResource {

    private final UserService userService;
    private final RoleService roleService;

    public UserResource(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUsers() {
        List<User> users = this.userService.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            Role role = roleService.findByUsername(user.getUsername());
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEnabled(), role.getAuthority());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    @PostMapping(value = "/api/admin/users")
    public void saveUser(@RequestParam(value = "username", defaultValue = "") String username) {
        User user = userService.findByUsername(username);
        userService.enableOrDisableUser(user);
    }

    @DeleteMapping("/api/admin/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }
}