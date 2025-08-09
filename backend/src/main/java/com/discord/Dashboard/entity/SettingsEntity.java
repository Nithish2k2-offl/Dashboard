package com.discord.Dashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    private String theme;

    @Column(name = "notification_prefs", columnDefinition = "TEXT")
    private String notificationPrefs; // store JSON string

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}