package digger.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import digger.model.Role;
import digger.model.User;
import digger.model.UserDTO;
import digger.model.enums.RoleKind;
import digger.service.RoleService;
import digger.service.UserService;

@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Value("${user.guide.url}")
    private String userGuideUrl;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin");
        return "admin/index";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-users");
        return "admin/users";
    }

    @PostMapping("/admin/users")
    public String saveUser(@ModelAttribute UserDTO user) {
        User existingUser = userService.findById(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : false);

        if (!user.getPassword().trim().isEmpty()) {
            existingUser = userService.changePassword(existingUser, user.getPassword());
            logger.info("Changed the password of the user {}", existingUser.getUsername());
        }

        RoleKind roleKind = RoleKind.valueOf(user.getMainRole());
        
        userService.save(existingUser, roleKind);

        return "redirect:/admin/users/"+ user.getId();
    }

    @GetMapping("/admin/users/{id}")
    public String openUser(Model model, @PathVariable Long id) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-user");
        UserDTO userDTO = createUserDTO(id, userService, roleService);
        model.addAttribute("user", userDTO);
        return "admin/user";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable Long id) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#admin-user");
        UserDTO userDTO = createUserDTO(id, userService, roleService);
        model.addAttribute("user", userDTO);
        return "admin/user_form";
    }

    private UserDTO createUserDTO(Long id, UserService userService, RoleService roleService) {
        User user = userService.findById(id);
        Role role = roleService.findByUsername(user.getUsername());
        return new UserDTO(user.getId(), user.getUsername(), user.getEnabled(), role.getAuthority());
    }
}