package digger.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void testGetId() {
        Role role = new Role();
        role.setId(3942l);
        assertThat(role.getId()).isEqualTo(3942l);
    }

    @Test
    public void testGetUsername() {
        Role role = new Role();
        role.setUsername("johnsmith");
        assertThat(role.getUsername()).isEqualTo("johnsmith");
    }

    @Test
    public void testGetAuthority() {
        Role role = new Role();
        role.setAuthority("admin");
        assertThat(role.getAuthority()).isEqualTo("admin");
    }
}