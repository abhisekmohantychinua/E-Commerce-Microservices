package dev.abhisek.productservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile file, String productId);

    byte[] fetchImage(String fileName, String productId);

    void updateImage(MultipartFile file, String fileName);
}
