package dev.abhisek.reviewservice.dto;

import jakarta.validation.constraints.*;

public record ReviewRequest(
        @NotNull(message = "Rating is a required field.")
        @Min(value = 0, message = "Rating can not go below 0.")
        @Max(value = 5, message = "Rating can not go above 5.")
        Integer rating,
        @NotNull(message = "Feedback is a required field.")
        @NotEmpty(message = "Feedback can not be empty.")
        @Size(max = 255, message = "Feedbacks above 255 characters not supported yet.")
        String feedback,
        String productId
) {
}
