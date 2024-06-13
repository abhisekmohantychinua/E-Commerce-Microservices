package dev.abhisek.productservice.mapper;

import dev.abhisek.productservice.dto.DetailDto;
import dev.abhisek.productservice.dto.ProductPage;
import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import dev.abhisek.productservice.entity.Category;
import dev.abhisek.productservice.entity.Detail;
import dev.abhisek.productservice.entity.Picture;
import dev.abhisek.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ProductPage toProductPage(Page<Product> productPage, int pageNo) {
        List<ProductResponse> products = new ArrayList<>();
        List<DetailDto> details = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        int maxPrice = 0;
        // Custom mapping and adding to products for more speed
        for (Product product : productPage.getContent()) {
            List<DetailDto> productDetails = new ArrayList<>();
            // Iterate details add it to productDetails and add to details if not in details
            for (Detail detail : product.getDetails()) {
                DetailDto productDetail = new DetailDto(detail.getTitle(), detail.getBody());
                productDetails.add(productDetail);
                if (!details.contains(productDetail)) {
                    details.add(productDetail);
                }
            }

            List<String> productCategories = new ArrayList<>();
            // Iterate categories add it to productCategories and add to categories if not in categories
            for (Category category : product.getCategories()) {
                String productCategory = category.getName();
                productCategories.add(productCategory);
                if (!categories.contains(productCategory)) {
                    categories.add(productCategory);
                }
            }

            ProductResponse response = new ProductResponse(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    productDetails,
                    product.getPrice(),
                    product.getQuantity(),
                    productCategories,
                    product.getPictures().stream()
                            .map(Picture::getPath)
                            .toList()
            );
            products.add(response);
        }

        return new ProductPage(
                pageNo,
                productPage.getTotalPages(),
                productPage.getNumber(),
                productPage.getNumberOfElements(),
                productPage.isFirst(),
                productPage.isLast(),
                products,
                details,
                categories,
                maxPrice
        );
    }
}
