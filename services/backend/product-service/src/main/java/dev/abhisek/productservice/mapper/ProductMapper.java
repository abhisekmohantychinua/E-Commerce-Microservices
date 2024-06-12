package dev.abhisek.productservice.mapper;

import dev.abhisek.productservice.dto.DetailDto;
import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import dev.abhisek.productservice.entity.Category;
import dev.abhisek.productservice.entity.Detail;
import dev.abhisek.productservice.entity.Picture;
import dev.abhisek.productservice.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        final String productId = UUID.randomUUID().toString();
        // Create product with basic contents
        Product product = Product.builder()
                .id(productId)
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .notHidden(true)
                .build();

        // Add details objects to product
        List<Detail> details = request.details().stream()
                .map(d -> Detail.builder()
                        .title(d.title())
                        .body(d.body())
                        .build())
                .toList();
        product.setDetails(details);

        List<Category> categories = request.categories().stream()
                .map(s -> Category.builder()
                        .name(s)
                        .product(Product.builder().id(productId).build())
                        .build())
                .toList();

        product.setCategories(categories);
        return product;
    }

    public ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getDetails().stream()
                        .map(d -> new DetailDto(d.getTitle(), d.getBody()))
                        .toList(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategories().stream()
                        .map(Category::getName)
                        .toList(),
                product.getPictures().stream()
                        .map(Picture::getPath)
                        .toList()
        );
    }
}
