package br.com.alura.forum.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.alura.forum.model.User;

@Controller
@CrossOrigin
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String index(@AuthenticationPrincipal User user) {
        System.out.println("Hello World!");
        return "Hello World com Spring Boot e MVC, " + user.getName();
    }
}