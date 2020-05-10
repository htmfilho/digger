package digger.model;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private Boolean enabled;
    private String mainRole;

    public UserDTO() {}

    public UserDTO(long id, String username, Boolean enabled, String mainRole) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.mainRole = mainRole;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMainRole() {
        return this.mainRole;
    }

    public void setMainRole(String role) {
        this.mainRole = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}