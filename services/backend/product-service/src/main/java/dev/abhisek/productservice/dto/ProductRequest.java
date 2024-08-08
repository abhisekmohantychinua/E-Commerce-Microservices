package dev.abhisek.productservice.dto;

import dev.abhisek.productservice.validator.CategoryField;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductRequest(
        @NotNull(message = "Title is a required field")
        @NotEmpty(message = "Title can not be empty.")
        @Size(max = 255, message = "Title length cannot exceed more than 255.")
        String title,
        @NotNull(message = "Description is a required field")
        @NotEmpty(message = "Description can not be empty.")
        String description,
        @NotNull(message = "Details is a required field")
        @NotEmpty(message = "Details must have at least one specification.")
        List<DetailDto> details,
        @NotNull(message = "Price is a required field")
        @NotEmpty(message = "Price can not be empty.")
        @Min(value = 1, message = "Price must be positive floating value greater than zero.")
        Double price,
        @NotNull(message = "Quantity is a required field")
        @NotEmpty(message = "Quantity can not be empty.")
        @Min(value = 1, message = "Quantity must be positive integer more than zero.")
        Integer quantity,
        @NotNull(message = "Categories is a required field")
        @NotEmpty(message = "Categories must have at least one category.")
        @CategoryField
        List<String> categories
) {
}
