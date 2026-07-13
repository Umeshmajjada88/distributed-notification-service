package com.umesh.distributed_notification_service.domain.notification.entity;

import com.umesh.distributed_notification_service.common.entity.BaseEntity;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification", indexes = {
        @Index(name = "idx_notification_user_id", columnList = "user_id"),
        @Index(name = "idx_notification_status", columnList = "status"),
        @Index(name = "idx_notification_channel", columnList = "channel"),
        @Index(name = "idx_notification_scheduled_at", columnList = "scheduled_at")
})
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "event_id", nullable = false, unique = true)
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationStatus status;

    @Column(length = 255)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;

}