package dev.abhisek.reviewservice.dto;

public record ReviewResponse(
        String id,
        int rating,
        String feedback,
        ProductResponse product,
        UserResponse user
) {
}
