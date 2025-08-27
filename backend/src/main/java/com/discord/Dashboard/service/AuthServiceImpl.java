package com.discord.Dashboard.service;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.dto.AuthResponse;
import com.discord.Dashboard.entity.TokenEntity;
import com.discord.Dashboard.entity.UserEntity;
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

    public AuthResponse register(AuthRequest dto) {
        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        userRepository.save(user);
        ensureNoActiveUserOrSameUser(user);

        return issueToken(user);
    }

    public AuthResponse login(AuthRequest dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow();

        ensureNoActiveUserOrSameUser(user);
        // Revoke old tokens
        List<TokenEntity> oldTokens = tokenRepository.findAllByUserAndRevokedFalse(user);
        oldTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(oldTokens);

        return issueToken(user);
    }

    public void logout(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or missing token.."));

        if(tokenEntity.isRevoked()){
            throw new RuntimeException("Token already revoked or expired...");
        }
        // Find the token
        List<TokenEntity> tokens = tokenRepository.findAllByUserAndRevokedFalse(tokenEntity.getUser());
        tokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(tokens);
    }

    private AuthResponse issueToken(UserEntity user) {
        String token = jwtService.generateToken(user.getEmail(), Map.of("username", user.getUsername()));

        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .revoked(false)
                .issuedAt(OffsetDateTime.now())
                .expiresAt(OffsetDateTime.now().plusHours(2))
                .user(user)
                .build();
        tokenRepository.save(tokenEntity);
        return new AuthResponse(token);
    }

    private void ensureNoActiveUserOrSameUser(UserEntity user) {
        List<TokenEntity> activeTokens = tokenRepository.findAllByRevokedFalse();
        if (!activeTokens.isEmpty()) {
            UserEntity activeUser = activeTokens.get(0).getUser();
            if (!activeUser.getId().equals(user.getId())) {
                throw new RuntimeException("Another user is already logged in. So log out first...");
            }
        }
    }


}
