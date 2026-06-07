package com.discord.Dashboard.service;

import com.discord.Dashboard.config.CustomUserDetails;
import com.discord.Dashboard.dto.SettingsRequestDto;
import com.discord.Dashboard.dto.SettingsResponseDto;
import com.discord.Dashboard.entity.SettingsEntity;
import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingsService {

    private final SettingsRepository repo;

    public SettingsResponseDto getMySettings() {
        UserEntity user = getCurrentUser();

        SettingsEntity settings = repo.findByUser(user)
                .orElseGet(() -> createDefaultSettings(user));

        return toDto(settings);

    }

    public SettingsResponseDto saveOrUpdate(SettingsRequestDto requestDto) {
        UserEntity user = getCurrentUser();

        SettingsEntity settings = repo.findByUser(user)
                .orElseGet(() -> SettingsEntity.builder()
                        .user(user)
                        .createdAt(OffsetDateTime.now())
                        .build());

        settings.setTheme(requestDto.getTheme());
        settings.setNotificationEnabled(requestDto.isNotificationEnabled());
        settings.setUpdatedAt(OffsetDateTime.now());

        SettingsEntity saved = repo.save(settings);

        return toDto(saved);
    }

    private SettingsEntity createDefaultSettings(UserEntity user) {
        SettingsEntity settings = SettingsEntity.builder()
                .user(user)
                .theme("Dark")
                .notificationEnabled(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        return repo.save(settings);

    }

    private UserEntity getCurrentUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        UserEntity user = new UserEntity();
        user.setId(userDetails.getId());

        return user;
    }

    private SettingsResponseDto toDto(SettingsEntity settings) {
        return SettingsResponseDto.builder()
                .id(settings.getId())
                .theme(settings.getTheme())
                .notificationEnabled(settings.isNotificationEnabled())
                .createdAt(settings.getCreatedAt())
                .updatedAt(settings.getUpdatedAt())
                .build();

    }

}
