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

import digger.model.Column;

import java.util.Comparator;

/**
 * ColumnComparator is used to sort columns in a way that the primary keys always come first in the list, independent
 * of the alphabetical order.
 * */
public class ColumnComparator implements Comparator<Column> {

    public int compare(Column previous, Column next) {
        if(next.getPrimaryKey() != null && next.getPrimaryKey())
            return 1;

        if(previous.getPrimaryKey() != null && previous.getPrimaryKey())
            return -1;

        return previous.compareTo(next);
    }
}
