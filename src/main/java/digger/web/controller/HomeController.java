package digger.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import digger.service.UserService;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @Value("${user.guide.url}")
    private String userGuideUrl;

    @GetMapping("/")
    public String index(Model model) {
        if(this.userService.thereIsNoUser()) {
            return "redirect:/signup";
        }

        model.addAttribute("userGuideUrl", userGuideUrl);
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl);
        return "error";
    }
}