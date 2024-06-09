package dev.abhisek.authorizationserver.user;

public record UserResponse(String id, String username, String password, String role) {
}
