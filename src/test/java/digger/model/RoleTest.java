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
    public void testSetAuthority() {
        Role role = new Role();
        role.setAuthority("role_admin");
        assertThat(role.getAuthority()).isEqualTo("ROLE_ADMIN");

        role = new Role();
        role.setAuthority(null);
        assertThat(role.getAuthority()).isNull();
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

        role.setAuthority(null);
        assertThat(role.getRoleKind()).isNull();
    }

    @Test
    public void testCustomEquals() {
        Role role = new Role();
        assertThat(role.equals(role)).isTrue();
        assertThat(role.equals(null)).isFalse();
        assertThat(role.equals(new User())).isFalse();

        Role roleA = new Role("A", "ROLE_ADMIN");
        Role roleB = new Role("B", "ROLE_ADMIN");
        assertThat(roleA.equals(roleB)).isFalse();

        Role roleC = new Role("B", "ROLE_EDITOR");
        assertThat(roleA.equals(roleC)).isFalse();
        assertThat(roleB.equals(roleC)).isFalse();

        Role roleAA = new Role("A", "ROLE_ADMIN");
        assertThat(roleA.equals(roleAA)).isTrue();
    }
}