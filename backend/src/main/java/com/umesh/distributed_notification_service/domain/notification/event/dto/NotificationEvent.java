package com.umesh.distributed_notification_service.domain.notification.event.dto;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements Serializable {

    private UUID eventId;

    private Long notificationId;

    private Long userId;

    private NotificationChannel channel;

    private String subject;

    private String message;

    private NotificationStatus status;

    private Integer retryCount;

    private LocalDateTime scheduledAt;

}