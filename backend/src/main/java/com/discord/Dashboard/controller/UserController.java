package com.discord.Dashboard.controller;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import com.discord.Dashboard.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository repo;

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("id/{id}")
    public void getUserById(@PathVariable UUID id) {
    }

    @PostMapping("/bind-discord")
    public ResponseEntity<String> bindDiscordId(@RequestBody Map<String, String> request, Authentication authentication) {
        String discordId = request.get("discordId");
        String email = authentication.getName();
        UserEntity user = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("hii"));
        System.out.println("The discordId is: " + discordId);
        userService.bindDiscordId(user.getId(), discordId);
        System.out.println("The userId is: " + user.getId());
        return ResponseEntity.ok("Discord account linked successfully");
    }
}
