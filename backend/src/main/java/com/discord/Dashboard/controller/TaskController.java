package com.discord.Dashboard.controller;

import com.discord.Dashboard.config.CustomUserDetails;
import com.discord.Dashboard.dto.TaskRequest;
import com.discord.Dashboard.dto.TaskResponse;
import com.discord.Dashboard.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.createTask(request, userDetails.getId()));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.getTasks(userDetails.getId()));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable UUID taskId, @RequestBody TaskRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request, userDetails.getId()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        taskService.deleteTask(taskId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
