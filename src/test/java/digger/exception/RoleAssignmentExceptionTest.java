package digger.exception;

import digger.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleAssignmentExceptionTest {

    @Test
    public void testGetUser() {
        User user = new User("john@smith.com");
        RoleAssignmentException roleAssignmentException = new RoleAssignmentException("Exception", user);
        assertEquals(user, roleAssignmentException.getUser());
    }

}