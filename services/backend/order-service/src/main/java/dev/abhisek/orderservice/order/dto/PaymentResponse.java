package dev.abhisek.orderservice.order.dto;

public record PaymentResponse(
        String id,
        Double amount,
        String paymentType,
        String createdAt,
        String status,
        String paymentCompletedAt
) {
}
