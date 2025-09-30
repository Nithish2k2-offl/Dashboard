package com.discord.Dashboard.service;

import com.discord.Dashboard.dto.NoteDto;
import com.discord.Dashboard.dto.NoteRequest;
import com.discord.Dashboard.entity.NoteEntity;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.NoteRepository;
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
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public List<NoteDto> getUserNotes(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return noteRepository.findByUser(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    public NoteDto createNote(UUID userId, NoteRequest request) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        NoteEntity noteEntity = NoteEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .user(user)
                .build();
        return toDto(noteRepository.save(noteEntity));
    }

    public NoteDto updateNote(UUID noteId, NoteRequest request, UUID userId) {
        NoteEntity note = noteRepository.findById(noteId).orElseThrow(() -> new EntityNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(userId)) {
            throw new SecurityException("You can't edit other user's notes");
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUpdatedAt(OffsetDateTime.now());

        return toDto(noteRepository.save(note));
    }

    public void deleteNote(UUID noteId, UUID userId) {
        NoteEntity note = noteRepository.findById(noteId).orElseThrow(() -> new EntityNotFoundException("note not found"));
        if (!note.getUser().getId().equals(userId)) {
            throw new SecurityException("You can't delete another user's note..");
        }
        noteRepository.deleteById(noteId);
    }

    private NoteDto toDto(NoteEntity entity) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(entity.getId());
        noteDto.setTitle(entity.getTitle());
        noteDto.setContent(entity.getContent());
        noteDto.setCreatedAt(entity.getCreatedAt());
        noteDto.setUpdatedAt(entity.getUpdatedAt());
        return noteDto;
    }
}
