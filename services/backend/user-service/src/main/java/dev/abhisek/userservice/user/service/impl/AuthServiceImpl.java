package dev.abhisek.userservice.user.service.impl;

import dev.abhisek.userservice.exceptions.models.UserNotFoundException;
import dev.abhisek.userservice.user.dto.AuthUserResponse;
import dev.abhisek.userservice.user.mapper.UserMapper;
import dev.abhisek.userservice.user.repo.UserRepository;
import dev.abhisek.userservice.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public AuthUserResponse findUserByUsername(String username) {
        return repository.findUserByEmail(username)
                .map(mapper::toAuthUserResponse)
                .orElseThrow(() -> new UserNotFoundException("No User found on server matching with username: " + username));
    }
}
