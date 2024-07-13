package dev.abhisek.authorizationserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth2")
public class AuthController {

    @GetMapping("sign-in")
    public String signIn() {
        return "signin";
    }

}
