package com.example.webserver.controller;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.dto.LoginRequest;
import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.service.UserService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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

            return ResponseEntity.ok().body(Map.of(
                    "message", "Correct login"
        ));

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

            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "message", "User registered"
            ));

        } catch (Exception e) {
            
            return ResposeEntity.ok().body(Map.of(
                "error", "User already exists"
            ));
        }
    }
}