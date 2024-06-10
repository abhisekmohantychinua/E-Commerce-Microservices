package dev.abhisek.userservice.user.service;

import dev.abhisek.userservice.user.dto.AuthUserResponse;

public interface AuthService {
    AuthUserResponse findUserByUsername(String username);
}
