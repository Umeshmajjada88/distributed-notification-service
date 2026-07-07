package com.umesh.distributed_notification_service.domain.notification.dto.response;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private UUID eventId;

    private Long userId;

    private NotificationChannel channel;

    private NotificationStatus status;

    private String subject;

    private String message;

    private Integer retryCount;

    private LocalDateTime scheduledAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}