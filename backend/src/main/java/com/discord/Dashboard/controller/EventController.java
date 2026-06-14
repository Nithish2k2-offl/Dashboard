package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.ApiResponse;
import com.discord.Dashboard.dto.EventDto;
import com.discord.Dashboard.dto.EventRequest;
import com.discord.Dashboard.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventDto>>> getUserEvents(@AuthenticationPrincipal(expression = "id") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "List of Events",
                                eventService.getUserEvents(userId)
                        )
                );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventDto>> createEvent(@AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody EventRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Event Created",
                                eventService.createEvent(userId, request)
                        )
                );
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDto>> updateEvent(@PathVariable UUID eventId, @AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody EventRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Event Updated",
                                eventService.updateEvent(eventId, request, userId)
                        )
                );
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@AuthenticationPrincipal(expression = "id") UUID userId, @PathVariable UUID eventId) {

        eventService.deleteEvent(eventId, userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Event Deleted",
                                null
                        )
                );
    }
}