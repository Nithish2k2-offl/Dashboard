package com.discord.Dashboard.controller;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.UserRepository;
import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        Optional<UserEntity> userOpt = userRepo.findByEmail(req.getEmail());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(req.getPassword())) {
            String token = jwtUtil.generateToken(req.getEmail());
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}

