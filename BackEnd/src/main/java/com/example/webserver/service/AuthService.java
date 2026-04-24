package com.example.webserver.service;

import com.example.webserver.entity.Role;
import com.example.webserver.entity.UserEntity;
import com.example.webserver.dto.AuthResponse;
import com.example.webserver.dto.LoginRequest;
import com.example.webserver.dto.RefreshTokenRequest;
import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.entity.RefreshToken;
import com.example.webserver.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserService userService,
                       RefreshTokenService refreshTokenService,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateToken(user.getUsername());

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userService.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserEntity user = new UserEntity(
                request.getUsername(),
                request.getPassword(),
                Role.USER
        );

        userService.saveUser(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateToken(user.getUsername());

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken oldToken = refreshTokenService.findByToken(request.getRefreshToken());

        refreshTokenService.verifyExpiration(oldToken);

        RefreshToken newToken = refreshTokenService.rotateRefreshToken(oldToken);

        UserEntity user = newToken.getUser();

        String accessToken = jwtService.generateToken(user.getUsername());

        return new AuthResponse(accessToken, newToken.getToken());
    }
}