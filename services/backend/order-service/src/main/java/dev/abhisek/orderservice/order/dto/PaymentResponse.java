package dev.abhisek.orderservice.order.dto;

public record PaymentResponse(
        String id,
        String status,
        String createdAt,
        String method,
        Double amount
) {
}
