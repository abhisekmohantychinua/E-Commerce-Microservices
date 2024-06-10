package dev.abhisek.userservice.user.service;

import dev.abhisek.userservice.user.dto.NewPasswordRequest;
import dev.abhisek.userservice.user.dto.UserRequest;
import dev.abhisek.userservice.user.dto.UserResponse;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);

    UserResponse findById(String id);

    void updatePassword(NewPasswordRequest request);
}
