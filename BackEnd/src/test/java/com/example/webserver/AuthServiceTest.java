package com.example.webserver;

import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.entity.RefreshToken;
import com.example.webserver.entity.UserEntity;
import com.example.webserver.exception.TokenExpiredException;
import com.example.webserver.repository.RefreshTokenRepository;
import com.example.webserver.repository.UserRepository;
import com.example.webserver.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import com.example.webserver.dto.AuthResponse;
import com.example.webserver.dto.LoginRequest;
import com.example.webserver.dto.LogoutRequest;
import com.example.webserver.dto.RefreshTokenRequest;

import org.springframework.security.authentication.BadCredentialsException;

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

    @Test
    void shouldLoginSuccessfully() {

        RegisterRequest registerRequest = 
                new RegisterRequest();
        
        registerRequest.setUsername("angel");
        registerRequest.setEmail("angel@test.com");
        registerRequest.setPassword("Angel123!");

        authService.register(registerRequest);

        LoginRequest loginRequest = 
                new LoginRequest();

        loginRequest.setUsername("angel");
        loginRequest.setPassword("Angel123!");

        AuthResponse authResponse = 
                authService.login(loginRequest);
        
        assertNotNull(authResponse);
        assertNotNull(authResponse.getAccessToken());
        assertNotNull(authResponse.getRefreshToken());
    }

    @Test
    void shouldFailLoginWithWrongPassword() {

        RegisterRequest registerRequest = 
                new RegisterRequest();

        registerRequest.setUsername("angel");
        registerRequest.setEmail("angel@test.com");
        registerRequest.setPassword("Angel123!");

        authService.register(registerRequest);

        LoginRequest loginRequest = 
                new LoginRequest();

        loginRequest.setUsername("angel");
        loginRequest.setPassword("WrongPassword123!");

        assertThrows(
            BadCredentialsException.class,
                () -> authService.login(loginRequest)
        );
    }

    @Test
    void shouldFailLoginWithNonExistingUser() {

        LoginRequest loginRequest = 
                new LoginRequest();

        loginRequest.setUsername("nonexistinguser");
        loginRequest.setPassword("SomePassword123!");

        assertThrows(
            BadCredentialsException.class,
                () -> authService.login(loginRequest)
        );
    }

    @Test
    void shouldGenerateAndPersistRefreshTokenOnLogin() {
    
        RegisterRequest registerRequest =
                new RegisterRequest();
    
        registerRequest.setUsername("angel");
        registerRequest.setEmail("angel@test.com");
        registerRequest.setPassword("Angel123!");
    
        authService.register(registerRequest);
    
        LoginRequest loginRequest =
                new LoginRequest();
    
        loginRequest.setUsername("angel");
        loginRequest.setPassword("Angel123!");
    
        AuthResponse authResponse =
                authService.login(loginRequest);
    
        assertNotNull(authResponse);
        assertNotNull(authResponse.getRefreshToken());
    
        String refreshToken =
                authResponse.getRefreshToken();
    
        var savedRefreshToken =
                refreshTokenRepository
                        .findByToken(refreshToken)
                        .orElse(null);
    
        assertNotNull(savedRefreshToken);
    
        assertEquals(
                "angel",
                savedRefreshToken
                        .getUser()
                        .getUsername()
        );
    }
    
    @Test 
    void shouldRotateRefreshTokenSuccessfully() {

    RegisterRequest registerRequest =
            new RegisterRequest();

    registerRequest.setUsername("angel");
    registerRequest.setEmail("angel@test.com");
    registerRequest.setPassword("Angel123!");

    authService.register(registerRequest);

    LoginRequest loginRequest =
            new LoginRequest();

    loginRequest.setUsername("angel");
    loginRequest.setPassword("Angel123!");

    AuthResponse loginResponse =
            authService.login(loginRequest);

    String oldRefreshToken =
            loginResponse.getRefreshToken();

    assertNotNull(oldRefreshToken);

    RefreshTokenRequest refreshRequest =
            new RefreshTokenRequest();

    refreshRequest.setRefreshToken(
            oldRefreshToken
    );

    AuthResponse refreshResponse =
            authService.refreshToken(
                    refreshRequest
            );

    assertNotNull(refreshResponse);
    assertNotNull(
            refreshResponse.getAccessToken()
    );
    assertNotNull(
            refreshResponse.getRefreshToken()
    );

    String newRefreshToken =
            refreshResponse.getRefreshToken();

    assertNotEquals(
            oldRefreshToken,
            newRefreshToken
    );

    boolean oldTokenExists =
            refreshTokenRepository
                    .findByToken(oldRefreshToken)
                    .isPresent();

    assertFalse(oldTokenExists);

    var savedNewToken =
            refreshTokenRepository
                    .findByToken(newRefreshToken)
                    .orElse(null);

    assertNotNull(savedNewToken);

    assertEquals(
            "angel",
            savedNewToken
                    .getUser()
                    .getUsername()
                );
        }
    
    @Test
    void shouldRevokeRefreshTokenOnLogout() {
        
        RegisterRequest registerRequest =
                new RegisterRequest();

        registerRequest.setUsername("angel");
        registerRequest.setEmail("angel@test.com");
        registerRequest.setPassword("Angel123!");

        authService.register(registerRequest);

        LoginRequest loginRequest =
                new LoginRequest();

        loginRequest.setUsername("angel");
        loginRequest.setPassword("Angel123!");

        AuthResponse loginResponse =
                authService.login(loginRequest);

        String refreshToken =
                loginResponse.getRefreshToken();

        assertNotNull(refreshToken);

        assertTrue(
                refreshTokenRepository
                        .findByToken(refreshToken)
                        .isPresent()
        );

        LogoutRequest logoutRequest =
                new LogoutRequest();

        logoutRequest.setRefreshToken(
                refreshToken
        );

        authService.logout(logoutRequest);

        boolean tokenStillExists =
                refreshTokenRepository
                        .findByToken(refreshToken)
                        .isPresent();

        assertFalse(tokenStillExists);
    }

    @Test
    void shouldFailRefreshWithNonExistingToken() {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken(
                "non-existing-token"
        );

        assertThrows(
                RuntimeException.class,
                () -> authService.refreshToken(
                        request
                )
        );
    }

    @Test
    void shouldFailRefreshWithExpiredToken() {
        
        RegisterRequest registerRequest =
            new RegisterRequest();
            
        registerRequest.setUsername("angel");
        registerRequest.setEmail("angel@test.com");
        registerRequest.setPassword("Angel123!");
        
        authService.register(registerRequest);

    LoginRequest loginRequest =
            new LoginRequest();

    loginRequest.setUsername("angel");
    loginRequest.setPassword("Angel123!");

    AuthResponse loginResponse =
            authService.login(loginRequest);

    String refreshToken =
            loginResponse.getRefreshToken();

    RefreshToken savedToken =
            refreshTokenRepository
                    .findByToken(refreshToken)
                    .orElseThrow();

    savedToken.setExpiryDate(
            Instant.now().minusSeconds(60)
    );

    refreshTokenRepository.save(savedToken);

    RefreshTokenRequest request =
            new RefreshTokenRequest();

    request.setRefreshToken(
            refreshToken
    );

    assertThrows(
        TokenExpiredException.class,
        () -> authService.refreshToken(
                request
        )
);

        boolean tokenStillExists =
                refreshTokenRepository
                        .findByToken(refreshToken)
                        .isPresent();

        assertFalse(tokenStillExists);
}  
}