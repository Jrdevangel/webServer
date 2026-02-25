package com.example.webserver.controller;

import java.security.Principal;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String home(Principal user) {
        String name = (user != null) ? user.getName() : "Guest";
        return "Welcome, " + name + "!";
    }

@PostMapping("/dashboard")
public Map<String, String> postDashboardMessage(Principal user) {
    String name = (user != null) ? user.getName() : "Guest";
    String message = dashboardService.dashboardMessage(name);
    return Map.of("message", message);
}
}