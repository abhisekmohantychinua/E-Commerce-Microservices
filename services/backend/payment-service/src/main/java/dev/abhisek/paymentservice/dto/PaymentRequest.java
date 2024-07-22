package dev.abhisek.paymentservice.dto;

public record PaymentRequest(
        Double amount,
        String paymentType,
        String userId
) {
}
