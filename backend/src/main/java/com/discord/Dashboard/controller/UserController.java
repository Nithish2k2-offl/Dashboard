package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.AuthRequest;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import com.discord.Dashboard.service.AuthServiceImpl;
import com.discord.Dashboard.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;
    private final UserRepository repo;

    @GetMapping("/getusers")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("getuser/{id}")
    public void getUserById(@PathVariable UUID id) {

    }

}
