package dev.abhisek.userservice.user.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRequest(
        String email,
        String password,
        String fullName,
        String role,
        MultipartFile profile
) {
}
