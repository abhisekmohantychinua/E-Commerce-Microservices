package dev.abhisek.userservice.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile profile, String fileName);

    byte[] fetchImage(String path);
}
