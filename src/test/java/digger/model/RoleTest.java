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

package digger.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import digger.model.enums.RoleKind;
import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void testGetId() {
        Role role = new Role();
        role.setId(3942l);
        assertThat(role.getId()).isEqualTo(3942l);
    }

    @Test
    public void testGetUsername() {
        Role role = new Role();
        role.setUsername("johnsmith");
        assertThat(role.getUsername()).isEqualTo("johnsmith");
    }

    @Test
    public void testGetAuthority() {
        Role role = new Role();
        role.setAuthority("role_admin");
        assertThat(role.getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void testInvalidAuthority() {
        Role role = new Role();
        assertThatThrownBy(() -> {
            role.setAuthority("admin");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetRoleKind() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        assertThat(role.getRoleKind()).isEqualTo(RoleKind.ROLE_ADMIN);
    }
}