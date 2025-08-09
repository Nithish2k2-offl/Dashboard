package com.discord.Dashboard.dto;

import lombok.Data;

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
