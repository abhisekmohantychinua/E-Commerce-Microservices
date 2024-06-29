package dev.abhisek.productservice.dto;

public record ProductPatchRequest(
        String id,
        Integer quantity
) {
}
