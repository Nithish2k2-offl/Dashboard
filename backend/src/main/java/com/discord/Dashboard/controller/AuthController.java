package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.repo.TokenRepository;
import com.discord.Dashboard.service.AuthServiceImpl;
import com.discord.Dashboard.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final TokenRepository tokenRepository;
    private final JwtServiceImpl jwtService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest dto) {
        try {
            return ResponseEntity.ok(authService.login(dto));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

}
