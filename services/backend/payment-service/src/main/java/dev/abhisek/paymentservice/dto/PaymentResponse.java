package dev.abhisek.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PaymentResponse(
        String id,
        Double amount,
        String paymentType,
        String createdAt,
        String status,
        String paymentCompletedAt
) {
}
