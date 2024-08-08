package dev.abhisek.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductCriteria(
        @NotNull(message = "Query must be provided.")
        String query,
        List<DetailDto> details,
        List<String> categories,
        @Min(value = 1, message = "Minimum price must be greater than 0.")
        Integer minPrice,
        Integer maxPrice,
        Boolean showOutOfStock
) {
}
