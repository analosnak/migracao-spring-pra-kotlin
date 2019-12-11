package br.com.alura.forum.controller

import br.com.alura.forum.model.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {
    @RequestMapping("/admin")
    @ResponseBody
    fun index(@AuthenticationPrincipal user: User) =
            "Hello World com Spring Boot e MVC, ${user.name}"
}