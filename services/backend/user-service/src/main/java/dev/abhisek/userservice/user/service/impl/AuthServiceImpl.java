package dev.abhisek.userservice.user.service.impl;

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
                .orElseThrow();// todo- configure exceptions
    }
}
