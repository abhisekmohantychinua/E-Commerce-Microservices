package dev.abhisek.orderservice.carts.dto;

public record ProductResponse(
        String id,
        String title,
        String description,
        Double price
) {
}
