package dev.abhisek.productservice.dto;

import java.util.List;

public record ProductPage(
        int pageNumber,
        int totalPage,
        int numberOfElements,
        int totalNumberOfElements,
        boolean isFirstPage,
        boolean isLastPage,
        List<ProductResponse> products,
        List<DetailDto> details,
        List<String> categories,
        int maxPrice
) {
}
