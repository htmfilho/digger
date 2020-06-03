package digger.exception;

import digger.model.User;

public class RoleAssignmentException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    private User user;

    public RoleAssignmentException(String message, User user) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}