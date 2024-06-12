package dev.abhisek.productservice.dto;

import java.util.List;

public record ProductResponse(
        String id,
        String title,
        String description,
        List<DetailDto> details,
        Double price,
        Integer quantity,
        List<String> categories,
        List<String> pictureNames
) {
}
