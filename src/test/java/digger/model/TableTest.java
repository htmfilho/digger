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

import org.junit.jupiter.api.Test;

public class TableTest {

    @Test
    public void testToString() {
        Table table = new Table("TABLE_A", "Table A");
        assertThat(table.toString()).isEqualTo(table.getName() +" ("+ table.getFriendlyName() +")");
    }

	@Test
	public void testCompareTo() {
        Table tableA = new Table("TABLE_A    ");
        Table tableAA = new Table("   TABLE_A");

        assertThat(tableA.compareTo(tableAA)).isEqualTo(0);
        
        Table tableB = new Table(" TABLE_B ");

        assertThat(tableA.compareTo(tableB)).isEqualTo(-1);
        assertThat(tableB.compareTo(tableA)).isEqualTo(1);

        Table table = new Table();

        assertThat(table.compareTo(tableA)).isEqualTo(-1);
        assertThat(tableA.compareTo(table)).isEqualTo(-1);
    }
    
    @Test
    public void testEquals() {
        Table tableA = new Table("TABLE_A   ");
        Table tableAA = new Table("  TABLE_A");

        assertThat(tableA.equals(tableAA)).isTrue();

        Table table1 = new Table(1L);
        Table table2 = new Table(2L);

        assertThat(table1.equals(table2)).isFalse();
        assertThat(table1.equals(table1)).isTrue();

        Table table = new Table(5L);
        assertThat(table.equals(table)).isTrue();
        assertThat(table.equals(tableA)).isFalse();
        assertThat(tableA.equals(table)).isFalse();

        assertThat(table.equals(new Column())).isFalse();
    }
}