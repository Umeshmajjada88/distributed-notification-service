package com.umesh.distributed_notification_service.domain.notification.event.publisher;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;

public interface NotificationEventPublisher {

    void publish(Notification notification);

}