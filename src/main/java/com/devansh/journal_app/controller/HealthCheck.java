package com.devansh.journal_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // all response of endpoints will automatically convert into JSON
public class HealthCheck {
    
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Working!";
    }
}
