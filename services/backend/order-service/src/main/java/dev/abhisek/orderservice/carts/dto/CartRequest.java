package dev.abhisek.orderservice.carts.dto;

public record CartRequest(
        String productId,
        Integer quantity
) {
}
