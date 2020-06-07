package digger.web.controller

import digger.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val userService: UserService) {

    @Value("\${user.guide.url}")
    lateinit var userGuideUrl: String

    @GetMapping("/")
    fun index(model: Model): String {
        if(userService.thereIsNoUser()) {
            return "redirect:/signup"
        }

        model.addAttribute("userGuideUrl", userGuideUrl)

        return "index"
    }

    @GetMapping("/error")
    fun error(model: Model): String {
        model.addAttribute("userGuideUrl", userGuideUrl)
        return "error"
    }
}