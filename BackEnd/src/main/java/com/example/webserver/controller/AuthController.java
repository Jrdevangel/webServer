package com.example.webserver.controller;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.dto.LoginRequest;
import com.example.webserver.dto.RefreshTokenRequest;
import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.entity.RefreshToken;
import com.example.webserver.security.JwtService;
import com.example.webserver.service.RefreshTokenService;
import com.example.webserver.service.UserService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.webserver.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          RefreshTokenService refreshTokenService,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
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

            return ResponseEntity.ok(
                new AuthResponse(accessToken, refreshToken.getToken())
            );

        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "error", "Incorrect credentials"
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {
            UserEntity user = new UserEntity(
                    request.getUsername(),
                    request.getPassword(),
                    Role.USER
            );
                
            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User registered"
            ));

        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "User already exists"
            ));
    }
}

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {

    RefreshToken oldToken = refreshTokenService.findByToken(request.getRefreshToken());

    refreshTokenService.verifyExpiration(oldToken);

    RefreshToken newToken = refreshTokenService.rotateRefreshToken(oldToken);

    UserEntity user = newToken.getUser();

    String accessToken = jwtService.generateToken(user.getUsername());

    return ResponseEntity.ok(
        new AuthResponse(accessToken, newToken.getToken())
    );
}
}