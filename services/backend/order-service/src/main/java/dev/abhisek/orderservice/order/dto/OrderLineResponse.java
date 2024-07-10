package dev.abhisek.orderservice.order.dto;

public record OrderLineResponse(
        ProductResponse product,
        Integer quantity
) {
}
