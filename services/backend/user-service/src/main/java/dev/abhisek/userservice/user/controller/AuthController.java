package dev.abhisek.userservice.user.controller;

import dev.abhisek.userservice.user.dto.AuthUserResponse;
import dev.abhisek.userservice.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @GetMapping
    public ResponseEntity<AuthUserResponse> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(service.findUserByUsername(username));
    }
}
