package com.umesh.delivery_service.infrastructure.kafka.consumer;

import com.umesh.delivery_service.domain.delivery.orchestrator.DeliveryOrchestrator;
import com.umesh.shared.event.NotificationRequestedEvent;
import com.umesh.shared.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRequestedConsumer {

    private final DeliveryOrchestrator orchestrator;

    @KafkaListener(topics = KafkaTopics.NOTIFICATION_REQUESTED, groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(NotificationRequestedEvent event) {

        log.info(
                "Received NotificationRequestedEvent {}",
                event.getEventId());

        orchestrator.process(event);

    }

}