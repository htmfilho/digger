package digger.web.controller;

import digger.model.User;
import digger.model.UserDTO;
import digger.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class IdentificationController {
    private static final Logger logger = LoggerFactory.getLogger(IdentificationController.class);

    private final UserService userService;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public IdentificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(this.userService.thereIsNoUser())
            return "redirect:/signup";

        model.addAttribute("userGuideUrl", userGuideUrl + "#login");
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("thereIsNoUser", this.userService.thereIsNoUser());
        model.addAttribute("userGuideUrl", userGuideUrl + "#signup");
        return "signup";
    }

    @PostMapping("/users/new")
    public String newUser(Model model, @ModelAttribute User user) {
        try {
            if(this.userService.thereIsNoUser())
                userService.saveAdmin(user.getUsername(), user.getPassword());
            else
                userService.saveReader(user.getUsername(), user.getPassword());
        } catch (RuntimeException re) {
            logger.error(re.getMessage(), re);
            model.addAttribute("user", user);
            model.addAttribute("thereIsNoUser", this.userService.thereIsNoUser());
            model.addAttribute("emailError", "A user with email '"+ user.getUsername() +"' already exists.");
            return "signup";
        }
        return "redirect:/";
    }

    @GetMapping("/users/profile")
    public String userProfile(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#profile");
        return "profile";
    }

    @GetMapping("/users/password")
    public String changePassword(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl + "#change-password");
        return "change_password";
    }

    @PostMapping("/users/password")
    public String changePassword(Model model, Principal principal, @ModelAttribute UserDTO user) {
        User existingUser = userService.findByUsername(principal.getName());

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder.matches(user.getCurrentPassword(), existingUser.getPassword())) {
            existingUser = userService.changePassword(existingUser, user.getPassword());
            logger.info("Changed the password of the user {}", existingUser.getUsername());
            userService.save(existingUser);
            return "redirect:/users/profile";
        }

        model.addAttribute("passwordMatchError", "Current password doesn't match.");
        return "change_password";
    }
}