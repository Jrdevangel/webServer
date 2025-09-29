package com.example.webserver.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webserver.service.DashboardService;

@RestController
public class WebServerController {

    private final DashboardService dashboardService;
    
    public WebServerController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome, jrdev_angel!";
    }

    @GetMapping("dashboard")
    public Map<String, String> getDashboardMenssage(Principal user) {
        String name = (user != null) ? user.getName() : "Guest";
        String message = dashboardService.dashboardMessage(name);
        return Map.of("message", message);
    }
}