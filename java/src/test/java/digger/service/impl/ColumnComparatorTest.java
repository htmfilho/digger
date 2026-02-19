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
import digger.model.ColumnComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnComparatorTest {

    @Test
    void testCompare() {
        Column previous = new Column(1L, "IDENTIFIER1", true);
        Column next = new Column(2L, "IDENTIFIER2", true);
        Column normal1 = new Column("NORMAL1");
        Column normal2 = new Column(3L, "NORMAL2", false);

        ColumnComparator columnComparator = new ColumnComparator();
        assertEquals(1, columnComparator.compare(null, next));

        assertEquals(-1, columnComparator.compare(previous, normal1));

        assertEquals(1, columnComparator.compare(normal2, normal1));

        assertEquals(-1, columnComparator.compare(normal1, normal2));
    }
}