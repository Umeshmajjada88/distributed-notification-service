package com.umesh.distributed_notification_service.infrastructure.kafka.publisher;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.publisher.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOutboxEventPublisher
        implements OutboxEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(OutboxEvent event) {

        kafkaTemplate.send(
                event.getTopic(),
                event.getAggregateId(),
                event.getPayload());

        log.info(
                "Published Outbox Event {} to topic {}",
                event.getId(),
                event.getTopic());
    }
}