package dev.abhisek.productservice.dto;

import java.util.List;

public record ProductCriteria(
        String query,
        List<DetailDto> details,
        List<String> categories,
        Integer minPrice,
        Integer maxPrice,
        Boolean showOutOfStock
) {
}
