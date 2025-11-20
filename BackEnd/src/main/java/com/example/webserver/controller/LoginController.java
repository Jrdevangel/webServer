package com.example.webserver.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
