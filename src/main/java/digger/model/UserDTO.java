package digger.model;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean enabled;
    private Role mainRole;

    public UserDTO() {}

    public UserDTO(long id, String username, Boolean enabled, Role mainRole) {
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

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getMainRole() {
        return this.mainRole;
    }

    public void setMainRole(Role role) {
        this.mainRole = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}