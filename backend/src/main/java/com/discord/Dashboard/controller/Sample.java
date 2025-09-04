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

    @GetMapping("/hi")
    public String sayHello() {
        return "Hii there!";
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable UUID id) {
        UserEntity obj = userRepository.findById(id).orElse(null);
        return ResponseEntity.ok(obj);
    }

}