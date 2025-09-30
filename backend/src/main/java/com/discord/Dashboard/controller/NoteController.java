package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.NoteDto;
import com.discord.Dashboard.dto.NoteRequest;
import com.discord.Dashboard.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public List<NoteDto> getUserNotes(@AuthenticationPrincipal(expression = "id") UUID id) {
        return noteService.getUserNotes(id);
    }

    @PostMapping
    public NoteDto createNote(@AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody NoteRequest request) {
        return noteService.createNote(userId, request);
    }

    @PutMapping("/{noteId}")
    public NoteDto updateNote(@PathVariable UUID noteId, @RequestBody NoteRequest request, @AuthenticationPrincipal(expression = "id") UUID userId) {
        return noteService.updateNote(noteId, request, userId);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@AuthenticationPrincipal(expression = "id") UUID userId, @PathVariable UUID noteId) {
        noteService.deleteNote(noteId, userId);
    }
}
