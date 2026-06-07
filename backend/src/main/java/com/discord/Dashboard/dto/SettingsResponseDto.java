package com.discord.Dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Data
public class SettingsResponseDto {

    private UUID id;
    private String theme;
    private boolean notificationEnabled;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
