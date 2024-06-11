package dev.abhisek.productservice.service.impl;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.entity.Category;
import dev.abhisek.productservice.entity.Detail;
import dev.abhisek.productservice.entity.Picture;
import dev.abhisek.productservice.entity.Product;
import dev.abhisek.productservice.repo.CategoryRepository;
import dev.abhisek.productservice.repo.ProductRepository;
import dev.abhisek.productservice.service.ImageService;
import dev.abhisek.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Override
    public String addProduct(ProductRequest request) {
        // Create product with basic contents
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .build();

        // Add details objects to product
        List<Detail> details = request.details().stream()
                .map(d -> Detail.builder()
                        .title(d.title())
                        .body(d.body())
                        .build())
                .toList();
        product.setDetails(details);

        List<Category> categories = categoryRepository.findByNameIn(request.categories());
        product.setCategories(categories);
        product = repository.save(product);
        return product.getId();
    }

    public List<String> addProductImages(MultipartFile[] files, String productId) {
        Product product = findProductByProductId(productId);
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            fileNames.add(imageService.saveImage(file, productId));
        }
        List<Picture> pictures = fileNames.stream().map(Picture::new).toList();
        product.setPictures(pictures);
        repository.save(product);
        return fileNames;
    }

    private Product findProductByProductId(String productId) {
        return repository.findById(productId)
                .orElseThrow();//todo- exception
    }
}
