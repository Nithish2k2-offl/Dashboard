package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.ApiResponse;
import com.discord.Dashboard.dto.NoteDto;
import com.discord.Dashboard.dto.NoteRequest;
import com.discord.Dashboard.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<NoteDto>>> getUserNotes(@AuthenticationPrincipal(expression = "id") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "List of notes for user ",
                                noteService.getUserNotes(userId)
                        )
                );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NoteDto>> createNote(@AuthenticationPrincipal(expression = "id") UUID userId, @RequestBody NoteRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Note created successfully",
                                noteService.createNote(userId, request)
                        )
                );
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<NoteDto>> updateNote(@PathVariable UUID noteId, @RequestBody NoteRequest request, @AuthenticationPrincipal(expression = "id") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Note Updated Successfully",
                                noteService.updateNote(noteId, request, userId)
                        )
                );
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@AuthenticationPrincipal(expression = "id") UUID userId, @PathVariable UUID noteId) {

        noteService.deleteNote(noteId, userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Note deleted successfully",
                                null
                        )
                );
    }
}
