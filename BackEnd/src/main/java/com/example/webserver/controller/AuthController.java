package com.example.webserver.controller;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.dto.LoginRequest;
import com.example.webserver.dto.RegisterRequest;
import com.example.webserver.service.UserService;

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
    public String login(@RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
            );

            return "Correct login";

        } catch (AuthenticationException e) {
            return "Incorrect credentials";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        UserEntity user = new UserEntity(
                request.getUsername(),
                request.getPassword(),
                Role.USER
        );

        userService.saveUser(user);

        return "User registered";
    }
}