package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.SettingsEntity;
import com.discord.Dashboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SettingsRepository extends JpaRepository<SettingsEntity, UUID> {

    Optional<SettingsEntity> findByUser(UserEntity user);

    boolean existsByUser(UserEntity user);
}
