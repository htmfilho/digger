package digger.model.enums;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedDriverClassTest {

    @Test
    public void testGetDriverClassName() {
        assertThat(SupportedDriverClass.H2.getDriverClassName()).isEqualTo("org.h2.Driver");
        assertThat(SupportedDriverClass.POSTGRES.getDriverClassName()).isEqualTo("org.postgresql.Driver");
        assertThat(SupportedDriverClass.SQLSERVER.getDriverClassName()).isEqualTo("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    @Test
    public void testGetListSupportedDriverClasses() {
        List<SupportedDriverClass> classes = SupportedDriverClass.getListSupportedDriverClasses();

        assertThat(classes.size()).isEqualTo(3);
        assertThat(classes).contains(SupportedDriverClass.H2);
        assertThat(classes).contains(SupportedDriverClass.POSTGRES);
        assertThat(classes).contains(SupportedDriverClass.SQLSERVER);
    }
}