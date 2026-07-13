package com.umesh.distributed_notification_service.infrastructure.kafka.resolver;

import com.umesh.distributed_notification_service.common.constants.KafkaTopics;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import org.springframework.stereotype.Component;

@Component
public class EventTopicResolver {

    public String resolve(NotificationEventType eventType) {

        return switch (eventType) {

            case NOTIFICATION_CREATED ->
                KafkaTopics.NOTIFICATION_CREATED;

            case NOTIFICATION_RETRY ->
                KafkaTopics.NOTIFICATION_RETRY;

            case NOTIFICATION_FAILED ->
                KafkaTopics.NOTIFICATION_DLQ;
        };
    }

}