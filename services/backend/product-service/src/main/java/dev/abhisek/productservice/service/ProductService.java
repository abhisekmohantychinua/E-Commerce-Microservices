package dev.abhisek.productservice.service;

import dev.abhisek.productservice.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);

    List<String> addProductImages(MultipartFile[] files, String productId);
}
