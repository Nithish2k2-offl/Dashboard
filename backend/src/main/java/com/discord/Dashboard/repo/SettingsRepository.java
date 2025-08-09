package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingsRepository extends JpaRepository<SettingsEntity, UUID> {
}
