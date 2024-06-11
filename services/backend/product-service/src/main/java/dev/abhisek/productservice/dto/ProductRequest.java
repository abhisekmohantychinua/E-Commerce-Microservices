package dev.abhisek.productservice.dto;

import java.util.List;

public record ProductRequest(
        String title,
        String description,
        List<DetailDto> details,
        Double price,
        Integer quantity,
        List<String> categories
) {
}
