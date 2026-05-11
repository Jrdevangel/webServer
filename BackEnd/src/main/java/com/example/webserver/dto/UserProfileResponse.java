package com.example.webserver.dto;

public class UserProfileResponse {
    private String username;
    private String role;
    
    public UserProfileResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
