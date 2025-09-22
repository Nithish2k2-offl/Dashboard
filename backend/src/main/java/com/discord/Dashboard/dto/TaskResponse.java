package com.discord.Dashboard.dto;

import com.discord.Dashboard.constants.TaskPriority;
import com.discord.Dashboard.constants.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private OffsetDateTime dueDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
