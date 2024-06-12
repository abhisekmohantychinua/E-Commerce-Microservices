package dev.abhisek.productservice.service;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);

    ProductResponse getProductDetails(String id);


    List<String> addProductImages(MultipartFile[] files, String productId);
}
