package digger.model;

import junit.framework.TestCase;
import org.junit.Assert;

public class RoleTest extends TestCase {

    public void testGetId() {
        Role role = new Role();
        role.setId(3942l);
        Assert.assertEquals(3942l, role.getId());
    }

    public void testGetUsername() {
        Role role = new Role();
        role.setUsername("johnsmith");
        Assert.assertEquals("johnsmith", role.getUsername());
    }

    public void testGetAuthority() {
        Role role = new Role();
        role.setAuthority("admin");
        Assert.assertEquals("admin", role.getAuthority());
    }
}