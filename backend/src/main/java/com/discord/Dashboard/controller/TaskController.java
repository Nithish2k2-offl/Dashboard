package com.discord.Dashboard.controller;

import com.discord.Dashboard.config.CustomUserDetails;
import com.discord.Dashboard.dto.ApiResponse;
import com.discord.Dashboard.dto.TaskRequest;
import com.discord.Dashboard.dto.TaskResponse;
import com.discord.Dashboard.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@RequestBody TaskRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Task Created successfully !",
                                taskService.createTask(request, userDetails.getId())
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasks(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Task - List",
                                taskService.getTasks(userDetails.getId())
                        )
                );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable UUID taskId, @RequestBody TaskRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Task Updated Successfully",
                                taskService.updateTask(taskId, request, userDetails.getId())
                        )
                );
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable UUID taskId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        taskService.deleteTask(taskId, userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Task Deleted successfully",
                                null)
                );
    }
}
