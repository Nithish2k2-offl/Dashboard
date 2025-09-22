package com.discord.Dashboard.service;

import com.discord.Dashboard.constants.TaskPriority;
import com.discord.Dashboard.constants.TaskStatus;
import com.discord.Dashboard.dto.TaskRequest;
import com.discord.Dashboard.dto.TaskResponse;
import com.discord.Dashboard.entity.TaskEntity;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.TaskRepository;
import com.discord.Dashboard.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest taskRequest, UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        TaskEntity task = TaskEntity.builder().title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getTaskStatus() != null ? taskRequest.getTaskStatus() : TaskStatus.TO_DO)
                .priority(taskRequest.getTaskPriority() != null ? taskRequest.getTaskPriority() : TaskPriority.LOW)
                .dueDate(taskRequest.getDueDate())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .user(user)
                .build();
        return toResponse(taskRepository.save(task));
    }

    private TaskResponse toResponse(TaskEntity task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .taskStatus(task.getStatus())
                .taskPriority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    public List<TaskResponse> getTasks(UUID userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse updateTask(UUID taskId, TaskRequest taskRequest, UUID userId) {
        TaskEntity task = taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getTaskStatus());
        task.setPriority(taskRequest.getTaskPriority());
        task.setDueDate(taskRequest.getDueDate());
        task.setUpdatedAt(OffsetDateTime.now());

        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(UUID taskId, UUID userId) {
        TaskEntity task = taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);

    }

}
