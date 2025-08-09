package com.discord.Dashboard.service;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(AuthRequest dto) {
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user.getEmail(), Map.of("username", user.getUsername()));

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest dto) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            System.out.println("Line 1 is done");
            var user = userRepository.findByEmail(dto.getEmail()).orElseThrow();
            System.out.println("Line 2 is done");
            var token = jwtService.generateToken(user.getEmail(), Map.of("username", user.getUsername()));
            System.out.println("Line 3 is done");
            System.out.println("token is = " + token);

            return new AuthResponse(token);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }
    }

}
