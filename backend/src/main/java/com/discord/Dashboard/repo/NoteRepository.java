package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.NoteEntity;
import com.discord.Dashboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findByUser(UserEntity user);
}
