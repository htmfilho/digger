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

package digger.model.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleKindTest {

    @Test
    public void testToString() {
        assertThat(RoleKind.ROLE_ADMIN.toString()).isEqualTo("ADMIN");
        assertThat(RoleKind.ROLE_EDITOR.toString()).isEqualTo("EDITOR");
        assertThat(RoleKind.ROLE_READER.toString()).isEqualTo("READER");
    }
}