package dev.abhisek.orderservice.order.dto;

import java.util.List;

public record OrderRequest(
        String addressId,
        List<ProductRequest> products,
        String paymentMethod
) {
}
