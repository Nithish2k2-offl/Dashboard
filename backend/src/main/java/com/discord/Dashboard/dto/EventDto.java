package com.discord.Dashboard.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class EventDto {
    private UUID id;
    private String title;
    private String description;
    private OffsetDateTime eventDate;
    private String location;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private OffsetDateTime reminderTime;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
