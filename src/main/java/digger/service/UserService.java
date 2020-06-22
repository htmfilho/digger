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

package digger.service;

import java.util.List;

import digger.model.enums.RoleKind;

public interface UserService {

    boolean thereIsNoUser();

    digger.model.User findById(Long id);
    digger.model.User findByUsername(String username);
    digger.model.User changePassword(digger.model.User user, String newPassword);

    List<digger.model.User> findAll();

    void save(digger.model.User user);
    void save(digger.model.User user, RoleKind roleKind);
    void registerAdmin(digger.model.User user);
    void registerReader(digger.model.User user);
    void enableOrDisableUser(digger.model.User user);
    void delete(Long id);
}