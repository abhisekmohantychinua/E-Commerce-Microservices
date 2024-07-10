package dev.abhisek.orderservice.order.dto;

import java.util.List;

public record OrderResponse(
        String id,
        Double totalAmount,
        String status,
        String user,
        AddressResponse address,
        PaymentResponse payment,
        String createdAt,
        List<OrderLineResponse> products
) {
}
