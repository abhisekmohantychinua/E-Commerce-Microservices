package dev.abhisek.userservice.user.service.impl;

import dev.abhisek.userservice.user.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${application.upload-path}")
    private String UPLOAD_PATH;
    @Value("${application.default-profile}")
    private String DEFAULT_PROFILE;


    @Override
    public String saveImage(MultipartFile profile, String fileName) {
        final String previousFileName = profile.getOriginalFilename();
        final String fileExtension = getFileExtension(previousFileName);

        File targetFolder = new File(UPLOAD_PATH);
        if (!targetFolder.exists()) {
            boolean created = targetFolder.mkdirs();
            if (!created) {
//                todo- exception
                return DEFAULT_PROFILE;
            }
        }
        final String newFileName = fileName + System.currentTimeMillis() + "." + fileExtension;
        final String targetPath = UPLOAD_PATH + separator + newFileName;
        Path path = Paths.get(targetPath);
        try {
            Files.write(path, profile.getBytes());
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);// todo- handle exception
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

    @Override
    public byte[] fetchImage(String filePath) {
        filePath = UPLOAD_PATH + separator + filePath;
        Path path = new File(filePath).toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);// todo-exception
        }
    }
}
