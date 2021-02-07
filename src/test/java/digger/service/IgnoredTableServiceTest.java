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

package digger.service;

import digger.model.IgnoredTable;
import digger.model.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IgnoredTableServiceTest {

    @Autowired
    IgnoredTableService ignoredTableService;

    @Test
    public void testToTableList() {
        List<IgnoredTable> ignoredTables = new ArrayList<>();
        ignoredTables.add(new IgnoredTable("BOOK"));
        ignoredTables.add(new IgnoredTable("AUTHOR"));
        List<Table> tables = ignoredTableService.toTableList(ignoredTables);
        assertEquals(ignoredTables.size(), tables.size());
        assertEquals(ignoredTables.get(0).getName(), tables.get(0).getName());
    }
}