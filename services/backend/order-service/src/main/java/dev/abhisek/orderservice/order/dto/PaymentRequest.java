package dev.abhisek.orderservice.order.dto;

public record PaymentRequest(
        Double amount,
        String paymentType,
        String userId
) {
}
