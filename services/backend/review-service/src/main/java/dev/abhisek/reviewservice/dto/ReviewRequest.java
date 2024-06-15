package dev.abhisek.reviewservice.dto;

public record ReviewRequest(
        Integer rating,
        String feedback,
        String productId
) {
}
