package dev.abhisek.productservice.controller;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import dev.abhisek.productservice.dto.ProductUpdateRequest;
import dev.abhisek.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.addProduct(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductDetails(id));
    }


    @PutMapping("{id}/pictures")
    public ResponseEntity<List<String>> updatePictures(@RequestParam MultipartFile[] files, @PathVariable String id) {
        return ResponseEntity.ok(service.addProductImages(files, id));
    }

    @GetMapping("{id}/pictures")
    public ResponseEntity<List<byte[]>> getPictures(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductImages(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable String id) {
        service.updateProduct(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/pictures")
    public ResponseEntity<Void> deleteProduct(@RequestParam String filename, @PathVariable String id) {
        service.removeProductImage(id, filename);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/hidden")
    public ResponseEntity<Void> toggleVisibility(@PathVariable String id) {
        service.toggleVisibility(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("hidden")
    public ResponseEntity<List<ProductResponse>> getHiddenProduct() {
        return ResponseEntity.ok(service.getAllHiddenProducts());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
