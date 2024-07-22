package dev.abhisek.paymentservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class AuthController {
    @GetMapping("signin")
    public String signin() {
        return "signin";
    }
}
