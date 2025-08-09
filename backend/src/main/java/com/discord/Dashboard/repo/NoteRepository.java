package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {
}
