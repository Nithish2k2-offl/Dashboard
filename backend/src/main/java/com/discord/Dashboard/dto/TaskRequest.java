package com.discord.Dashboard.dto;

import com.discord.Dashboard.constants.TaskPriority;
import com.discord.Dashboard.constants.TaskStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TaskRequest {
    private String  title;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private OffsetDateTime dueDate;

}
