package com.discord.Dashboard.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EventRequest {
    private String title;
    private String description;
    private OffsetDateTime eventDate;
    private String location;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private OffsetDateTime reminderTime;
}

