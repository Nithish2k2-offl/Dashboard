package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.UserRegisterDto;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    // To register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto){
        UserEntity user = authService.register(dto);
        return ResponseEntity.ok("User added..."+dto.getUsername());
    }
}
