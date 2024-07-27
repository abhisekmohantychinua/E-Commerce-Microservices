package dev.abhisek.paymentservice.dto;

import java.util.List;

public record OrderResponse(
        String id,
        Double totalAmount,
        String status,
        String createdAt,
        List<OrderLineResponse> products
) {
}
