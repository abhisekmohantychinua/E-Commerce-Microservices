package dev.abhisek.productservice.service.impl;

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
    @Value("${application.default-profile}")
    private String DEFAULT_PROFILE;

    @Override
    public String saveImage(MultipartFile file, String productId) {
        final String previousFileName = file.getOriginalFilename();
        final String fileExtension = getFileExtension(previousFileName);

        File targetFolder = new File(UPLOAD_PATH + separator + productId);
        if (!targetFolder.exists()) {
            boolean created = targetFolder.mkdirs();
            if (!created) {
//                todo- exception
                return DEFAULT_PROFILE;
            }
        }
        final String newFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + fileExtension;
        final String targetPath = UPLOAD_PATH + separator + productId + separator + newFileName;
        Path path = Paths.get(targetPath);
        try {
            Files.write(path, file.getBytes());
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);// todo- handle exception
        }
    }

    @Override
    public byte[] fetchImage(String fileName, String productId) {
        final String filePath = UPLOAD_PATH + separator + productId + separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException();// todo-exception
        }
        Path path = file.toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);// todo-exception
        }
    }

    @Override
    public void removeImage(String fileName, String productId) {
        final String filePath = UPLOAD_PATH + separator + productId + separator + fileName;
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);//
        }

    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new RuntimeException(); // todo- exception
        }
        final int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new RuntimeException(); // todo- exception
        }
        return fileName
                .substring(dotIndex + 1)
                .toLowerCase();
    }
}
