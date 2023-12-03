package com.boney.passkey.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/passkey")
public class PasskeyController {

    @PostMapping("/register")
    public String registerUser(@RequestParam String userId, @RequestParam String passkey) {
        // Implement user registration logic
        return "User registered successfully";
    }

    @PostMapping("/verify")
    public String verifyPasskey(@RequestParam String userId, @RequestParam String passkey) {
        // Implement passkey verification logic
        return "Passkey verified successfully";
    }
}