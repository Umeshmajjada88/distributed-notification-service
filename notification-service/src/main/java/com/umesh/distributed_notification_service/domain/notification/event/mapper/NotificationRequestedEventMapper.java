package com.umesh.distributed_notification_service.domain.notification.event.mapper;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.shared.event.NotificationRequestedEvent;
import com.umesh.shared.types.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestedEventMapper {

    public NotificationRequestedEvent toEvent(Notification notification) {

        return NotificationRequestedEvent.builder()
                .eventId(notification.getEventId())
                .notificationId(notification.getId())
                .userId(notification.getUserId())
                .channel(NotificationChannel.valueOf(notification.getChannel().name()))
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .build();
    }
}