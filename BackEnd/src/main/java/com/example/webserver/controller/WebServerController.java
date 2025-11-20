package com.example.webserver.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.webserver.service.DashboardService;

@RestController
public class WebServerController {

    private final DashboardService dashboardService;
    
    public WebServerController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public RedirectView redirectToHome() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome, jrdev_angel!";
    }

@PostMapping("/dashboard")
public Map<String, String> postDashboardMessage(Principal user) {
    String name = (user != null) ? user.getName() : "Guest";
    String message = dashboardService.dashboardMessage(name);
    return Map.of("message", message);
}

@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
    String username = credentials.get("username");
    String password = credentials.get("password");

    if ("jrdev_angel".equals(username) && "password".equals(password)) {
        return ResponseEntity.ok(Map.of("message", "Login successful"));
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(Map.of("message", "Invalid credentials"));
    }
}
}