package com.discord.Dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

//import com.vladmihalcea.hibernate.type.json.JsonType;
//import org.hibernate.annotations.Type;
//@Column(name = "notification_prefs", columnDefinition = "jsonb")
//private Map<String, Object> notificationPrefs;
/*
* notificationPrefs store json data , for the single user
* like this.
* {
  "email": true,
  "sms": false,
  "discordDM": true,
  "dailySummaryTime": "08:00"
}
*/

@Entity
@Table(name = "settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    private String theme;

    @Column
    private boolean notificationEnabled;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}