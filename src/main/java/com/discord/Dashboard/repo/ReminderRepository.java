package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReminderRepository extends JpaRepository<ReminderEntity, UUID> {
}
