package dev.abhisek.paymentservice.dto;

public record UserProfileResponse(
        String email,
        String fullName,
        String role,
        byte[] profile
) {
}
