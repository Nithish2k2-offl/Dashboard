package com.discord.Dashboard.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
