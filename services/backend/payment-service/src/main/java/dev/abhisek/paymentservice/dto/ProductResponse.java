package dev.abhisek.paymentservice.dto;

import java.util.List;

public record ProductResponse(
        String id,
        String title,
        String description,
        Double price,
        List<String> categories,
        Integer quantity
) {
}
