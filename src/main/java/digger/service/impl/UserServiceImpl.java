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

package digger.service.impl;

import digger.exception.RoleAssignmentException;
import digger.model.Role;
import digger.model.User;
import digger.model.enums.RoleKind;
import digger.repository.UserRepository;
import digger.service.RoleService;
import digger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserDetailsManager userDetailsManager, UserRepository userRepository, RoleService roleService) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public boolean thereIsNoUser() {
        return userRepository.countAllByEnabled(true) == 0;
    }

    public Long countAll() {
        return userRepository.count();
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    public digger.model.User findById(final Long id) {
        return userRepository.findById(id);
    }

    public digger.model.User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public void save(digger.model.User user) {
        userRepository.save(user);
        logger.info("Saved user {}", user.getUsername());
    }

    @Transactional
    public void save(digger.model.User user, RoleKind newRole) {
        // Abort if the current user is the only administrator and the role has changed.
        Role currentRole = roleService.findByUsername(user.getUsername());
        // Check whether the current role is admin and whether the new role is different from admin.
        if(RoleKind.ROLE_ADMIN.name().equals(currentRole.getAuthority()) && !currentRole.getRoleKind().equals(newRole)) {
            long numAdmins = roleService.countAllByAuthority(RoleKind.ROLE_ADMIN.name());
            // If there one or less admin, it is not ok to change the role of the current admin.
            if (numAdmins <= 1) {
                throw new RoleAssignmentException("You are the only administrator and cannot change your own role. " +
                                                  "Digger requires at least 2 administrators to allow this operation.", user);
            }
        }

        currentRole.setAuthority(newRole.name());
        roleService.save(currentRole);
        
        userRepository.save(user);
        logger.info("Saved user {}", user.getUsername());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        logger.info("Deleted user with id {}", id);
    }

    public void registerAdmin(digger.model.User user) {
        register(user, RoleKind.ROLE_ADMIN, false);

        logger.info("Created admin user {}", user.getUsername());
    }

    public void registerReader(digger.model.User user) {
        register(user, RoleKind.ROLE_READER, true);

        logger.info("Created reader user {}", user.getUsername());
    }

    private void register(digger.model.User user, RoleKind role, boolean disabled) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(role.toString())
                .disabled(disabled)
                .build();
        this.userDetailsManager.createUser(userDetails);

        digger.model.User existingUser = userRepository.findByUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        userRepository.save(existingUser);
    }

    public void enableOrDisableUser(digger.model.User user) {
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
        logger.info("User {} was {}", user.getUsername(), user.getEnabled() ? "Enabled": "Disabled");
    }

    public digger.model.User changePassword(digger.model.User user, String newPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    public List<String> exportToSql() {
        List<String> sqlStatements = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();
        for (User user: allUsers) {
            StringBuilder sqlStatement = new StringBuilder();
            sqlStatement.append("insert into users (id, username, password, enabled, created, first_name, last_name) values (");
            sqlStatement.append(user.getId());
            sqlStatement.append(",'");
            sqlStatement.append(user.getUsername());
            sqlStatement.append("','");
            sqlStatement.append(user.getPassword());
            sqlStatement.append("', ");
            sqlStatement.append(user.getEnabled());
            sqlStatement.append(",'");
            sqlStatement.append(user.getCreated());
            sqlStatement.append("','");
            sqlStatement.append(user.getFirstName());
            sqlStatement.append("','");
            sqlStatement.append(user.getLastName());
            sqlStatement.append("');");
            sqlStatements.add(sqlStatement.toString());
        }
        sqlStatements.add("\n");
        return sqlStatements;
    }
}
