package com.example.webserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService {

    public boolean canAccessUser(
        String username,
        Authentication authentication
    ) {

        if (authentication == null) {
            return false;
        }

        boolean isOwner = username.equals(authentication.getName());

        boolean isAdmin = authentication.getAuthorities()
            .stream()
            .anyMatch(authority -> 
                        authority.getAuthority().equals("ROLE_ADMIN"));

        return isOwner || isAdmin;
    }
}
