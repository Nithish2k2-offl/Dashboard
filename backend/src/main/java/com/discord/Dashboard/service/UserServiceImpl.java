package com.discord.Dashboard.service;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
}
