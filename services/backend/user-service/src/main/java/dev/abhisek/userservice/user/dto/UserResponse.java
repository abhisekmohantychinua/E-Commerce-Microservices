package dev.abhisek.userservice.user.dto;

public record UserResponse(
        String email,
        String fullName,
        String role,
        String profileUrl
) {
}
