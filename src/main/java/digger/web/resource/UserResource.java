package digger.web.resource;

import digger.model.User;
import digger.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return this.userService.findAll();
    }

    @PostMapping(value = "admin/api/users")
    public void saveUser(@RequestParam(value = "username", defaultValue = "") String username) {
        User user = userService.findByUsername(username);
        userService.enableOrDisableUser(user);
    }
}