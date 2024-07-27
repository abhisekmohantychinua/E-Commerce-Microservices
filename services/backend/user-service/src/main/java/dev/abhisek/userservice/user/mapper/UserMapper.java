package dev.abhisek.userservice.user.mapper;

import dev.abhisek.userservice.user.dto.AuthUserResponse;
import dev.abhisek.userservice.user.dto.UserResponse;
import dev.abhisek.userservice.user.models.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserMapper {
    public AuthUserResponse toAuthUserResponse(User user) {
        return new AuthUserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name()
        );
    }

    public UserResponse toUserResponse(User user, byte[] profile, String mimeType) {
        return new UserResponse(
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                getProfileUrl(profile, mimeType)
        );
    }

    private String getProfileUrl(byte[] profile, String mimeType) {
        final String prefix = "data:";
        final String mime = mimeType + ";";
        final String encodingIndicator = "base64,";
        final String data = Base64.getEncoder().encodeToString(profile);
        final String url = prefix + mime + encodingIndicator + data;
        return url;
    }
}
