package dev.abhisek.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DetailDto(
        @NotNull(message = "Detail title is a required field.")
        @NotEmpty(message = "Detail title can not be empty.")
        @Size(max = 255, message = "Detail title cannot exceed 255 length.")
        String title,
        @NotNull(message = "Detail body is a required field.")
        @NotEmpty(message = "Detail body can not be empty.")
        @Size(max = 255, message = "Detail body cannot exceed 255 length.")
        String body
) {
}
