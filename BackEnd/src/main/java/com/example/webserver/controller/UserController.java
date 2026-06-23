package com.example.webserver.controller;

import com.example.webserver.dto.UserProfileResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/profile")
    public UserProfileResponse profile(Authentication authentication) {

        String username = authentication.getName();

        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(java.util.stream.Collectors.toSet());

        return new UserProfileResponse(
            username, 
            roles
        );
    }
}