package com.discord.Dashboard.controller;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Sample {
    @Autowired
    private final UserRepository userRepository;

    public Sample(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/hellov1")
    public String sayHello() {
        return "Hello v1";
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity UserEntity) {
        System.out.println("hi");
        UserEntity obj = userRepository.save(UserEntity);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/getUser/{id}")
    public UserEntity getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }


}