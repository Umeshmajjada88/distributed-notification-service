package com.umesh.distributed_notification_service.domain.notification.event.mapper;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventMapper {

    public NotificationEvent toEvent(Notification notification) {

        return NotificationEvent.builder()
                .eventId(notification.getEventId())
                .notificationId(notification.getId())
                .userId(notification.getUserId())
                .channel(notification.getChannel())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .retryCount(notification.getRetryCount())
                .scheduledAt(notification.getScheduledAt())
                .build();
    }

}