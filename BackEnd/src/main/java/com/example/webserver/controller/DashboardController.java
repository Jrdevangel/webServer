package com.example.webserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class DashboardController {

    @ResponseBody
    public String dashboard() {
        return "Welcome to your admin Dashboard!";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
