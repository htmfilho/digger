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

package digger.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum SupportedDriverClass {
    H2("org.h2.Driver"),
    POSTGRES("org.postgresql.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    private final String driverClassName;

    SupportedDriverClass(final String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public static List<SupportedDriverClass> getListSupportedDriverClasses() {
        List<SupportedDriverClass> supportedDriverClasses = new ArrayList<>(3);
        supportedDriverClasses.add(H2);
        supportedDriverClasses.add(POSTGRES);
        supportedDriverClasses.add(SQLSERVER);
        return supportedDriverClasses;
    }
}