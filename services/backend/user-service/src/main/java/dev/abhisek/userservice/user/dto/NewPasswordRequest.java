package dev.abhisek.userservice.user.dto;

public record NewPasswordRequest(
        String password,
        String rePassword
) {
}
