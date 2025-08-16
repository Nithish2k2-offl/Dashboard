package com.discord.Dashboard.service;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.entity.ActiveSession;
import com.discord.Dashboard.entity.TokenEntity;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.ActiveSessionRepository;
import com.discord.Dashboard.repo.TokenRepository;
import com.discord.Dashboard.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authManager;
    private final ActiveSessionRepository activeSessionRepository;

    public AuthResponse register(AuthRequest dto) {
        if (activeSessionRepository.findTopByOrderByIdAsc().isPresent()) {
            throw new RuntimeException("from register...Another user already logged in. Logout first.");
        }
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), Map.of("username", user.getUsername()));
        // Save the token in the database
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(tokenEntity);

        ActiveSession session = new ActiveSession();
        session.setUser(user);
        session.setStartedAt(OffsetDateTime.now());
        activeSessionRepository.save(session);

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest dto) {
        try {
            activeSessionRepository.findTopByOrderByIdAsc().ifPresent(session -> {
                if (!session.getUser().getEmail().equals(dto.getEmail())) {
                    throw new RuntimeException("from login...Another user already logged in. Logout first.");
                }
            });
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow();

            // Revoke old tokens
            List<TokenEntity> oldTokens = tokenRepository.findAllByUserAndRevokedFalse(user);
            oldTokens.forEach(t -> t.setRevoked(true));
            tokenRepository.saveAll(oldTokens);

            // Issue new token
            String token = jwtService.generateToken(user.getEmail(), Map.of("username", user.getUsername()));

            // Save new token
            TokenEntity tokenEntity = TokenEntity.builder()
                    .token(token)
                    .revoked(false)
                    .user(user)
                    .build();
            tokenRepository.save(tokenEntity);

            if (activeSessionRepository.findTopByOrderByIdAsc().isEmpty()) {
                ActiveSession session = new ActiveSession();
                session.setUser(user);
                session.setStartedAt(OffsetDateTime.now());
                activeSessionRepository.save(session);
            }

            return new AuthResponse(token);

        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }
    }

    public void logout(String token) {
        // Find the token
        TokenEntity tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or missing token"));

        UserEntity user = tokenEntity.getUser();

        // Revoke all active tokens for this user (idempotent)
        List<TokenEntity> tokens = tokenRepository.findAllByUserAndRevokedFalse(user);
        if (!tokens.isEmpty()) {
            tokens.forEach(t -> t.setRevoked(true));
            tokenRepository.saveAll(tokens);
        }

        // Clear active session (safe even if already deleted)
        activeSessionRepository.deleteAll();
    }


}
