package dev.abhisek.orderservice.carts.dto;

public record CartResponse(
        ProductResponse product,
        Integer quantity
) {
}
