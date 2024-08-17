package dev.abhisek.orderservice.carts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull(message = "Product id is a required field.")
        @NotEmpty(message = "Product id cannot be empty.")
        String productId,
        @Min(value = 1, message = "At least one product should be added to cart.")
        Integer quantity
) {
}
