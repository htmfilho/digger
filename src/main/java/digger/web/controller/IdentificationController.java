package digger.web.controller;

import digger.model.User;
import digger.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IdentificationController {

    @Value("${user.guide.url}")
    private String userGuideUrl;

    private UserService userService;

    public IdentificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl);
        if(this.userService.thereIsNoUser())
            return "redirect:/signup";

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userGuideUrl", userGuideUrl);
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
            model.addAttribute("user", user);
            model.addAttribute("userGuideUrl", userGuideUrl);
            model.addAttribute("thereIsNoUser", this.userService.thereIsNoUser());
            model.addAttribute("emailError", "User with email '"+ user.getUsername() +"' already exists.");
            return "signup";
        }
        return "redirect:/";
    }
}
