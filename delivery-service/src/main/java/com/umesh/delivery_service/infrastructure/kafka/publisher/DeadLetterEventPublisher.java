package com.umesh.delivery_service.infrastructure.kafka.publisher;

import com.umesh.shared.event.NotificationDeadLetterEvent;

public interface DeadLetterEventPublisher {

    void publish(NotificationDeadLetterEvent event);

}