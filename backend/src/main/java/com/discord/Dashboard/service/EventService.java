package com.discord.Dashboard.service;

import com.discord.Dashboard.dto.EventDto;
import com.discord.Dashboard.dto.EventRequest;
import com.discord.Dashboard.entity.EventEntity;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.EventRepository;
import com.discord.Dashboard.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<EventDto> getUserEvents(UUID userId) {
        UserEntity user = getUser(userId);
        return eventRepository.findByUser(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    public EventDto createEvent(UUID userId, EventRequest request) {

        UserEntity user = getUser(userId);

        EventEntity event = EventEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reminderTime(request.getReminderTime())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .user(user)
                .build();

        return toDto(eventRepository.save(event));
    }

    public EventDto updateEvent(UUID eventId, EventRequest request, UUID userId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(("Event not found")));
        if (!event.getUser().getId().equals(userId)) {
            throw new SecurityException("Your can't edit another user's event");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setReminderTime(request.getReminderTime());
        event.setUpdatedAt(OffsetDateTime.now());

        return toDto(eventRepository.save(event));
    }

    public void deleteEvent(UUID eventId, UUID userId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(("Event not found")));

        if (!event.getUser().getId().equals(userId)) {
            throw new SecurityException("Your can't delete another user's event");
        }
        eventRepository.delete(event);
    }

    private UserEntity getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private EventDto toDto(EventEntity entity) {
        EventDto dto = new EventDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setEventDate(entity.getEventDate());
        dto.setLocation(entity.getLocation());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setReminderTime(entity.getReminderTime());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
