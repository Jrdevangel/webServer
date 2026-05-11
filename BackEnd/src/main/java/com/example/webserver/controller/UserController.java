package com.example.webserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webserver.dto.UserProfileResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public UserProfileResponse profile(Authentication authentication) {
        
        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("NO_ROLE");

        return new UserProfileResponse(username, role);
    }
}
