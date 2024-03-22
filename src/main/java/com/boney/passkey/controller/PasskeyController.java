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
        // lazy implementation
        return "User registered successfully";
    }

    @PostMapping("/verify")
    public String verifyPasskey(@RequestParam String userId, @RequestParam String passkey) {
        // lazy implementation
        return "Passkey verified successfully";
    }
}
