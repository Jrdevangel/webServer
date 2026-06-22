package com.example.webserver.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.webserver.service.DashboardService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@Hidden
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public RedirectView redirectToHome() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String home(Principal principal) {

        String username = principal != null
                ? principal.getName() 
                : "Guest";
        
        return "Welcome, " + username + "!";
    }

    @PostMapping("/dashboard")
    public Map<String, String> postDashboardMessage(Principal principal) {

        String username = principal != null
                ? principal.getName()
                : "Guest";

        String message = dashboardService.dashboardMessage(username);

        return Map.of("message", message);
    }
}