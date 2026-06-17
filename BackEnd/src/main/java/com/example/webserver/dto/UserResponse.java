package com.example.webserver.dto;

import com.example.webserver.entity.Role;

public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final Role role;

    public UserResponse(
            Long id,
            String username,
            String email,
            Role role
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}