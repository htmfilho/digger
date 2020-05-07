package digger.web.controller;

import digger.model.User;
import digger.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IdentificationController {

    private static final Logger log = LoggerFactory.getLogger(IdentificationController.class.getName());

    private final UserService userService;

    public IdentificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(this.userService.thereIsNoUser())
            return "redirect:/signup";

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("thereIsNoUser", this.userService.thereIsNoUser());
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
            log.error(re.getMessage(), re);
            model.addAttribute("user", user);
            model.addAttribute("thereIsNoUser", this.userService.thereIsNoUser());
            model.addAttribute("emailError", "A user with email '"+ user.getUsername() +"' already exists.");
            return "signup";
        }
        return "redirect:/";
    }
}
