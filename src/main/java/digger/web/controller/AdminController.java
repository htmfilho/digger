package digger.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

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
}