package dev.abhisek.productservice.service;

import dev.abhisek.productservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);

    List<ProductResponse> getProductDetails(List<String> productIds);

    ProductResponse getProductDetails(String id);


    List<String> addProductImages(MultipartFile[] files, String productId);


    List<byte[]> getProductImages(String id);

    void patchProductQuantity(List<ProductPatchRequest> requests);

    void updateProduct(String id, ProductUpdateRequest request);

    void removeProductImage(String id, String filename);

    void toggleVisibility(String id);

    List<ProductResponse> getAllHiddenProducts();

    void deleteProduct(String id);

    ProductPage searchProduct(ProductCriteria criteria, Integer pageNo);
}
