package digger.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IgnoredTableTest {

    @Test
    public void ignoredTableTest() {
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
    public void toTableTest() {
        IgnoredTable ignoredTable = new IgnoredTable("A Table");
        Table table = ignoredTable.toTable();
        assertThat(ignoredTable.getName()).isEqualTo(table.getName());
    }

	@Test
	public void compareToTest() {
        IgnoredTable ignoredTableA = new IgnoredTable("IGNORED_TABLE_A    ");
        IgnoredTable ignoredTableAA = new IgnoredTable("   IGNORED_TABLE_A");

        assertThat(ignoredTableA.compareTo(ignoredTableAA)).isEqualTo(0);
        
        IgnoredTable ignoredTableB = new IgnoredTable(" IGNORED_TABLE_B ");

        assertThat(ignoredTableA.compareTo(ignoredTableB)).isEqualTo(-1);
        assertThat(ignoredTableB.compareTo(ignoredTableA)).isEqualTo(1);

        IgnoredTable ignoredTable = new IgnoredTable();

        assertThat(ignoredTable.compareTo(ignoredTableA)).isEqualTo(-1);
    }
    
    @Test
    public void equalsTest() {
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