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

package digger.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class IgnoredTableTest {

    @Test
    public void testIgnoredTable() {
        Datasource datasource = new Datasource();
        IgnoredTable ignoredTable = new IgnoredTable("TABLE", datasource);
        ignoredTable.setId(30L);
        ignoredTable.setName("VIEW");
        ignoredTable.setDatasource(new Datasource());

        assertThat(ignoredTable.getName()).isEqualTo("VIEW");
        assertThat(ignoredTable.getDatasource()).isNotEqualTo(datasource);
        assertThat(ignoredTable.getId()).isEqualTo(30L);
        assertThat(ignoredTable.toString()).isEqualTo(ignoredTable.getName());
    }

    @Test
    public void testToTable() {
        IgnoredTable ignoredTable = new IgnoredTable("A Table");
        Table table = ignoredTable.toTable();
        assertThat(ignoredTable.getName()).isEqualTo(table.getName());
    }

	@Test
	public void testCompareTo() {
        IgnoredTable ignoredTableA = new IgnoredTable("IGNORED_TABLE_A    ");
        IgnoredTable ignoredTableAA = new IgnoredTable("   IGNORED_TABLE_A");

        assertThat(ignoredTableA.compareTo(ignoredTableAA)).isEqualTo(0);
        
        IgnoredTable ignoredTableB = new IgnoredTable(" IGNORED_TABLE_B ");

        assertThat(ignoredTableA.compareTo(ignoredTableB)).isEqualTo(-1);
        assertThat(ignoredTableB.compareTo(ignoredTableA)).isEqualTo(1);

        IgnoredTable ignoredTable = new IgnoredTable(5L);

        assertThat(ignoredTable.compareTo(ignoredTableA)).isEqualTo(-1);
        assertThat(ignoredTableA.compareTo(ignoredTable)).isEqualTo(-1);
        assertThat(ignoredTable.compareTo(ignoredTable)).isEqualTo(0);
    }
    
    @Test
    public void testCustomEquals() {
        IgnoredTable ignoredTableA = new IgnoredTable("IGNORED_TABLE_A   ");
        IgnoredTable ignoredTableAA = new IgnoredTable("  IGNORED_TABLE_A");

        assertThat(ignoredTableA.equals(ignoredTableAA)).isTrue();

        IgnoredTable ignoredTable1 = new IgnoredTable(1L);
        IgnoredTable ignoredTable2 = new IgnoredTable(2L);

        assertThat(ignoredTable1.equals(ignoredTable2)).isFalse();
        assertThat(ignoredTable1.equals(ignoredTable1)).isTrue();

        IgnoredTable ignoredTable = new IgnoredTable();
        assertThat(ignoredTable.equals(ignoredTable)).isTrue();

        Table table = new Table();
        assertThat(ignoredTable.equals(table)).isFalse();
    }
}