package dev.abhisek.productservice.service.impl;

import dev.abhisek.productservice.exceptions.models.UnsupportedImageFormatException;
import dev.abhisek.productservice.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.io.File.separator;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${application.upload-path}")
    private String UPLOAD_PATH;

    @Override
    public String saveImage(MultipartFile file, String productId) {
        final String previousFileName = file.getOriginalFilename();
        final String fileExtension = getFileExtension(previousFileName);

        File targetFolder = new File(UPLOAD_PATH + separator + productId);
        if (!targetFolder.exists()) {
            boolean created = targetFolder.mkdirs();
            if (!created) {
                throw new RuntimeException("Cannot create target folder for request");
            }
        }
        final String newFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + fileExtension;
        final String targetPath = UPLOAD_PATH + separator + productId + separator + newFileName;
        Path path = Paths.get(targetPath);
        try {
            Files.write(path, file.getBytes());
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] fetchImage(String fileName, String productId) {
        final String filePath = UPLOAD_PATH + separator + productId + separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException();
        }
        Path path = file.toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeImage(String fileName, String productId) {
        final String filePath = UPLOAD_PATH + separator + productId + separator + fileName;
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new UnsupportedImageFormatException("There is no filename provided for image.");
        }
        final int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new UnsupportedImageFormatException("The filename is invalid.");
        }
        String extension = fileName
                .substring(dotIndex + 1)
                .toLowerCase();
        if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png") && !extension.equalsIgnoreCase("jpeg")) {
            throw new UnsupportedImageFormatException("This image format '" + extension + "' is not supported.");
        }
        return extension;
    }
}
