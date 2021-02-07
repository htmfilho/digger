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

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedDriverClassTest {

    @Test
    public void testGetDriverClassName() {
        assertThat(SupportedDriverClass.H2.getDriverClassName()).isEqualTo("org.h2.Driver");
        assertThat(SupportedDriverClass.POSTGRES.getDriverClassName()).isEqualTo("org.postgresql.Driver");
        assertThat(SupportedDriverClass.SQLSERVER.getDriverClassName()).isEqualTo("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    @Test
    public void testGetListSupportedDriverClasses() {
        List<SupportedDriverClass> classes = SupportedDriverClass.getListSupportedDriverClasses();

        assertThat(classes.size()).isEqualTo(3);
        assertThat(classes).contains(SupportedDriverClass.H2);
        assertThat(classes).contains(SupportedDriverClass.POSTGRES);
        assertThat(classes).contains(SupportedDriverClass.SQLSERVER);
    }
}