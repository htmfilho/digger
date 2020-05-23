package digger.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TableTest {

	@Test
	public void compareToTest() {
        Table tableA = new Table("TABLE_A    ");
        Table tableAA = new Table("   TABLE_A");

        assertThat(tableA.compareTo(tableAA)).isEqualTo(0);
        
        Table tableB = new Table(" TABLE_B ");

        assertThat(tableA.compareTo(tableB)).isEqualTo(-1);
        assertThat(tableB.compareTo(tableA)).isEqualTo(1);

        Table table = new Table();

        assertThat(table.compareTo(tableA)).isEqualTo(-1);
    }
    
    @Test
    public void equalsTest() {
        Table tableA = new Table("TABLE_A   ");
        Table tableAA = new Table("  TABLE_A");

        assertThat(tableA.equals(tableAA)).isTrue();

        Table table1 = new Table(1L);
        Table table2 = new Table(2L);

        assertThat(table1.equals(table2)).isFalse();
        assertThat(table1.equals(table1)).isTrue();

        Table table = new Table();
        assertThat(table.equals(table)).isTrue();
    }
}