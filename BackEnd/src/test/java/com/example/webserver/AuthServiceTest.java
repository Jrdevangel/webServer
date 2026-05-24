package com.example.webserver;

import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.entity.UserEntity;
import com.example.webserver.repository.RefreshTokenRepository;
import com.example.webserver.repository.UserRepository;
import com.example.webserver.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void cleanDatabase() {
        refreshTokenRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void shouldRegisterNewUserSuccessfully() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("angel");
        request.setEmail("angel@test.com");
        request.setPassword("Angel123!");

        authService.register(request);

        UserEntity savedUser =
                userRepository.findByUsername("angel")
                        .orElse(null);

        assertNotNull(savedUser);
        assertEquals("angel", savedUser.getUsername());
        assertEquals("angel@test.com", savedUser.getEmail());
    }
}