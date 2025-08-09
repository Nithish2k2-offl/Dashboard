package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest dto) {
        return authService.login(dto);
    }
}
