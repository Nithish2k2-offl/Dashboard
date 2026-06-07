package com.discord.Dashboard.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SettingsRequestDto {

    @NotBlank(message = "Theme is required")
    private String theme;

    private boolean notificationEnabled;
}
