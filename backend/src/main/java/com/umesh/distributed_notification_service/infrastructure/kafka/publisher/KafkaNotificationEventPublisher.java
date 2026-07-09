package com.umesh.distributed_notification_service.infrastructure.kafka.publisher;

import com.umesh.distributed_notification_service.common.constants.KafkaTopics;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.notification.event.publisher.NotificationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaNotificationEventPublisher
        implements NotificationEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final NotificationEventMapper eventMapper;

    @Override
    public void publish(Notification notification) {

        NotificationEvent event = eventMapper.toEvent(notification);

        kafkaTemplate.send(
                KafkaTopics.NOTIFICATION_CREATED,
                notification.getEventId().toString(),
                event);

        log.info(
                "Notification event published to Kafka. eventId={}",
                notification.getEventId());
    }
}