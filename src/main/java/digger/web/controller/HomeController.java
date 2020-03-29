package digger.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${user.guide.url}")
    private String userGuideUrl;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl);
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("userGuideUrl", userGuideUrl);
        return "error";
    }
}