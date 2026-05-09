package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.EventEntity;
import com.discord.Dashboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByUser(UserEntity user);
}
