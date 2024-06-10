package dev.abhisek.userservice.user.mapper;

import dev.abhisek.userservice.user.dto.AuthUserResponse;
import dev.abhisek.userservice.user.dto.UserResponse;
import dev.abhisek.userservice.user.models.User;
import org.springframework.stereotype.Service;

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

    public UserResponse toUserResponse(User user, byte[] profile) {
        return new UserResponse(
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                profile
        );
    }
}
