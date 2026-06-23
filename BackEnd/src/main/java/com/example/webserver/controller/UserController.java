package com.example.webserver.controller;

import com.example.webserver.dto.UserProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

import com.example.webserver.entity.UserEntity;
import com.example.webserver.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
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

    @GetMapping("/{username}")
    public UserProfileResponse getUserProfile(
        @PathVariable String username
    ) {

    UserEntity user = userService.getUserByUsername(username);

    Set<String> roles = user.getRoles()
            .stream()
            .map(role -> "ROLE_" + role.name())
            .collect(java.util.stream.Collectors.toSet());

    return new UserProfileResponse(
            user.getUsername(),
            roles
        );
    }
}