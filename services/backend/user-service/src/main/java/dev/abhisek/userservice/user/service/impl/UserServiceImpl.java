package dev.abhisek.userservice.user.service.impl;

import dev.abhisek.userservice.exceptions.models.UnsupportedImageFormatException;
import dev.abhisek.userservice.exceptions.models.UserNotFoundException;
import dev.abhisek.userservice.user.dto.NewPasswordRequest;
import dev.abhisek.userservice.user.dto.UserRequest;
import dev.abhisek.userservice.user.dto.UserResponse;
import dev.abhisek.userservice.user.mapper.UserMapper;
import dev.abhisek.userservice.user.models.Role;
import dev.abhisek.userservice.user.models.User;
import dev.abhisek.userservice.user.repo.UserRepository;
import dev.abhisek.userservice.user.service.ImageService;
import dev.abhisek.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final ImageService imageService;


    @Override
    public UserResponse saveUser(UserRequest request) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.email())
                .password(request.password())
                .fullName(request.fullName())
                .role(Role.valueOf(request.role()))
                .profile(saveProfileImage(request.profile(), request.email()))
                .build();

        user = repository.save(user);
        byte[] profile = imageService.fetchImage(user.getProfile());
        return mapper.toUserResponse(user, profile, extractMimeType(user.getProfile()));
    }

    @Override
    public UserResponse findById(String id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found on server with matching id: " + id));
        byte[] profile = imageService.fetchImage(user.getProfile());
        return mapper.toUserResponse(user, profile, extractMimeType(user.getProfile()));
    }

    @Override
    public void updatePassword(NewPasswordRequest request) {

    }

    private String saveProfileImage(MultipartFile profile, String email) {
        String fileName = email.replace("@", "--");
        fileName = fileName.replace(".", "-");
        return imageService.saveImage(profile, fileName);
    }

    private String extractMimeType(String profile) {
        String extension = profile.split("[.]")[1];
        String mimeType;
        if (extension.equalsIgnoreCase("png"))
            mimeType = MediaType.IMAGE_PNG_VALUE;
        else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg"))
            mimeType = MediaType.IMAGE_JPEG_VALUE;
        else
            throw new UnsupportedImageFormatException("The image format is not supported.");
        return mimeType;
    }

}
