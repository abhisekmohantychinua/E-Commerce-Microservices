package dev.abhisek.productservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile file);

    byte[] fetchImage(String fileName);

    void updateImage(MultipartFile file, String fileName);
}
