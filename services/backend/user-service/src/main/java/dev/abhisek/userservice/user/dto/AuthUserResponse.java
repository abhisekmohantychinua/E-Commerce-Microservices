package dev.abhisek.userservice.user.dto;

public record AuthUserResponse(
        String id,
        String username,
        String password,
        String role
) {
}
