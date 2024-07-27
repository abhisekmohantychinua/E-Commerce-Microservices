package dev.abhisek.paymentservice.dto;

public record OrderLineResponse(
        ProductResponse product,
        Integer quantity
) {
}
