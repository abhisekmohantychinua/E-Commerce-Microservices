package dev.abhisek.productservice.service;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import dev.abhisek.productservice.dto.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);

    ProductResponse getProductDetails(String id);


    List<String> addProductImages(MultipartFile[] files, String productId);


    List<byte[]> getProductImages(String id);

    void updateProduct(String id, ProductUpdateRequest request);

    void removeProductImage(String id, String filename);
}
