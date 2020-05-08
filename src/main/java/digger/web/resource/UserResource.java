package digger.web.resource;

import digger.model.Role;
import digger.model.User;
import digger.model.UserDTO;
import digger.service.RoleService;
import digger.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserResource {

    private final UserService userService;
    private final RoleService roleService;

    public UserResource(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUsers() {
        List<User> users = this.userService.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            Role role = roleService.findByUsername(user.getUsername());
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEnabled(), role);
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    @PostMapping(value = "admin/api/users")
    public void saveUser(@RequestParam(value = "username", defaultValue = "") String username) {
        User user = userService.findByUsername(username);
        userService.enableOrDisableUser(user);
    }
}