package com.discord.Dashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "event_date")
    private OffsetDateTime eventDate;

    private String location;

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    @Column(name = "reminder_time", nullable = true)
    private OffsetDateTime reminderTime;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}

