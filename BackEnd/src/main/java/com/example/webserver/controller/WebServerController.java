package com.example.webserver.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webserver.service.DashboardService;

@RestController
public class WebServerController {

    private final DashboardService dashboardService;
    
    public WebServerController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome, jrdev_angel!";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Principal user) {
        return dashboardService.dashboardMessage(user.getName());
    }
}