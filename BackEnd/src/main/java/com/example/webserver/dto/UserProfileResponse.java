package com.example.webserver.dto;

import java.util.Set;

public record UserProfileResponse(
        String username,
        Set<String> roles
) {
}