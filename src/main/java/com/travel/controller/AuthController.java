package com.travel.controller;

import com.travel.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        auth.register(body.get("username"), body.get("password"));
        return Map.of("message", "Registered successfully");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        return Map.of("token", auth.login(body.get("username"), body.get("password")));
    }
}
