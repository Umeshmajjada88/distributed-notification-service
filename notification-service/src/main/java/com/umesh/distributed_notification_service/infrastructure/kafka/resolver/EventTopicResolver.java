package com.umesh.distributed_notification_service.infrastructure.kafka.resolver;

import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.shared.kafka.KafkaTopics;
import org.springframework.stereotype.Component;

@Component
public class EventTopicResolver {

    public String resolve(NotificationEventType eventType) {

        return switch (eventType) {

            case NOTIFICATION_REQUESTED ->
                    KafkaTopics.NOTIFICATION_REQUESTED;
        };
    }
}