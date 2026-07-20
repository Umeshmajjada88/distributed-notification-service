package com.umesh.delivery_service.infrastructure.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umesh.delivery_service.domain.delivery.orchestrator.DeliveryOrchestrator;
import com.umesh.shared.event.NotificationRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRequestedConsumer {

    private final DeliveryOrchestrator orchestrator;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notification.requested", groupId = "delivery-service", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String payload) throws Exception {

        NotificationRequestedEvent event = objectMapper.readValue(
                payload,
                NotificationRequestedEvent.class);

        log.info(
                "Received NotificationRequestedEvent: {}",
                event.getEventId());

        orchestrator.process(event);
    }
}