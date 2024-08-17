package dev.abhisek.orderservice.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotNull(message = "Product id is a required field.")
        @NotEmpty(message = "Product id cannot be empty.")
        String productId,
        @Min(value = 1, message = "At least one product should be requested.")
        @NotNull(message = "Quantity is a required field.")
        int quantity
) {
}
