package com.discord.Dashboard.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRegisterDto {
    private String email;
    private String username;
    private String password;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
