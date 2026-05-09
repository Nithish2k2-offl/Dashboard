package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.EventDto;
import com.discord.Dashboard.dto.EventRequest;
import com.discord.Dashboard.service.EventService;
import lombok.RequiredArgsConstructor;
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
    public List<EventDto> getUserEvents(@AuthenticationPrincipal(expression = "id") UUID userId) {
        return eventService.getUserEvents(userId);
    }

    @PostMapping
    public EventDto createEvent(@AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody EventRequest request) {
        return eventService.createEvent(userId, request);
    }

    @PutMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable UUID eventId, @AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody EventRequest request) {
        return eventService.updateEvent(eventId, request, userId);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@AuthenticationPrincipal(expression = "id") UUID userId, @PathVariable UUID eventId) {
        eventService.deleteEvent(eventId, userId);
    }
}