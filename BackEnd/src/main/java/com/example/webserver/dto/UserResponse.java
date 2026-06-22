package com.example.webserver.dto;

import java.util.Set;

import com.example.webserver.entity.Role;

public record UserResponse(
        Long id,
        String username,
        String email,
        Set<Role> roles
) {
}