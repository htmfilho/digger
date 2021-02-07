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

import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Test
    public void testToString() {
        Column column = new Column("COLUMN_A", "Column A");
        assertThat(column.toString()).isEqualTo(column.getName() +" ("+ column.getFriendlyName() +")");
    }

	@Test
	public void testCompareTo() {
        Column columnA = new Column("COLUMN_A    ");
        Column columnAA = new Column("   COLUMN_A");

        assertThat(columnA.compareTo(columnAA)).isEqualTo(0);
        
        Column columnB = new Column(" COLUMN_B ");

        assertThat(columnA.compareTo(columnB)).isEqualTo(-1);
        assertThat(columnB.compareTo(columnA)).isEqualTo(1);

        Column unnamedColumn = new Column();

        assertThat(unnamedColumn.compareTo(unnamedColumn)).isEqualTo(-1);
        assertThat(columnA.compareTo(unnamedColumn)).isEqualTo(-1);
    }
    
    @Test
    public void testCustomEquals() {
        Column columnA = new Column("COLUMN_A   ");
        Column columnAA = new Column("  COLUMN_A");

        assertThat(columnA.equals(columnAA)).isTrue();

        Column column1 = new Column(1L);
        Column column2 = new Column(2L);

        assertThat(column1.equals(column2)).isFalse();
        assertThat(column1.equals(column1)).isTrue();

        Column column = new Column();
        assertThat(column.equals(column)).isTrue();
        assertThat(columnA.equals(column)).isTrue();

        assertThat(columnA.equals(new Table())).isFalse();
    }
}