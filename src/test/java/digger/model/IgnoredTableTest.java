package digger.model;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IgnoredTableTest {

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

        IgnoredTable ignoredTable1 = new IgnoredTable(1l);
        IgnoredTable ignoredTable2 = new IgnoredTable(2l);

        assertThat(ignoredTable1.equals(ignoredTable2)).isFalse();
        assertThat(ignoredTable1.equals(ignoredTable1)).isTrue();

        IgnoredTable ignoredTable = new IgnoredTable();
        assertThat(ignoredTable.equals(ignoredTable)).isTrue();
    }
}