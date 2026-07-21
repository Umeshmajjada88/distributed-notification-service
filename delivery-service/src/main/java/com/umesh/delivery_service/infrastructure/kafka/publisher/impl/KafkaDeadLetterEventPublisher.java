package com.umesh.delivery_service.infrastructure.kafka.publisher.impl;

import com.umesh.delivery_service.infrastructure.kafka.publisher.DeadLetterEventPublisher;
import com.umesh.shared.event.NotificationDeadLetterEvent;
import com.umesh.shared.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDeadLetterEventPublisher
        implements DeadLetterEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(NotificationDeadLetterEvent event) {

        kafkaTemplate.send(
                KafkaTopics.NOTIFICATION_DLQ,
                event.getEventId().toString(),
                event);

        log.info(
                "Published Dead Letter Event for delivery {}",
                event.getDeliveryId());

    }

}