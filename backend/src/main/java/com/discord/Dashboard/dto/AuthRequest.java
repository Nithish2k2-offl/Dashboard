package com.discord.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AuthRequest {
    private String email;
    private String username;
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
