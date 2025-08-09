package com.discord.Dashboard.controller;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class Sample {
    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/helloAgain")
    public String sayHello() {
        return "Hello Again!";
    }

    @PostMapping("/adduser")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity UserEntity) {
        UserEntity obj = userRepository.save(UserEntity);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable UUID id) {
        UserEntity obj = userRepository.findById(id).orElse(null);
        return ResponseEntity.ok(obj);
    }

}