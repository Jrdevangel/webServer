package com.example.webserver.service;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    public String dashboardMessage(String username) {
        return "Welcome to the admin dashboard, " + username + ".";
    }
}
