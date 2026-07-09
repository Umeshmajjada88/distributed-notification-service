package com.umesh.distributed_notification_service.domain.notification.mapper;

import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(CreateNotificationRequest request) {

        if (request == null) {
            return null;
        }

        return Notification.builder()
                .userId(request.getUserId())
                .channel(request.getChannel())
                .subject(request.getSubject())
                .message(request.getMessage())
                .scheduledAt(request.getScheduledAt())
                .build();
    }

    public NotificationResponse toResponse(Notification notification) {

        if (notification == null) {
            return null;
        }

        return NotificationResponse.builder()
                .id(notification.getId())
                .eventId(notification.getEventId())
                .userId(notification.getUserId())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .retryCount(notification.getRetryCount())
                .scheduledAt(notification.getScheduledAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}