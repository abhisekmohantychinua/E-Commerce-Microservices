package dev.abhisek.userservice.user.controller;

import dev.abhisek.userservice.user.dto.NewPasswordRequest;
import dev.abhisek.userservice.user.dto.UserRequest;
import dev.abhisek.userservice.user.dto.UserResponse;
import dev.abhisek.userservice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserResponse> createUser(@ModelAttribute @Valid UserRequest request) {
        return ResponseEntity.ok(service.saveUser(request));
    }

    @GetMapping("profile")
    public ResponseEntity<UserResponse> profile(@RequestHeader("user_id") String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("password")
    public ResponseEntity<Void> updatePassword(@RequestBody NewPasswordRequest request) {
        service.updatePassword(request);
        return ResponseEntity.noContent().build();
    }
}
