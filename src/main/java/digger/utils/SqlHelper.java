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

package digger.utils;

import java.util.Date;

public class SqlHelper {

    public static String fieldToSql(Integer field, String suffix) {
        if (field == null)
            return "null" + suffix;

        return field.toString() + suffix;
    }

    public static String fieldToSql(Long field, String suffix) {
        if (field == null)
            return "null" + suffix;

        return field.toString() + suffix;
    }

    public static String fieldToSql(Boolean field, String suffix) {
        if (field == null)
            return "null" + suffix;

        return field.toString() + suffix;
    }

    public static String fieldToSql(String field, String suffix) {
        if (field == null)
            return "null" + suffix;

        return "'"+ field + "'" + suffix;
    }

    public static String fieldToSql(Date field, String suffix) {
        if (field == null)
            return "null" + suffix;

        return "'"+ field + "'" + suffix;
    }
}
