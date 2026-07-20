package com.umesh.distributed_notification_service.infrastructure.kafka.publisher;

import com.umesh.distributed_notification_service.common.serialization.JsonSerializer;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.publisher.OutboxEventPublisher;
import com.umesh.shared.event.NotificationRequestedEvent;
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

    private final JsonSerializer jsonSerializer;

    @Override
    public void publish(OutboxEvent outboxEvent) {

        NotificationRequestedEvent event =
                jsonSerializer.deserialize(
                        outboxEvent.getPayload(),
                        NotificationRequestedEvent.class);

        kafkaTemplate.send(
                outboxEvent.getTopic(),
                outboxEvent.getAggregateId(),
                event);

        log.info(
                "Published Outbox Event {} to topic {}",
                outboxEvent.getId(),
                outboxEvent.getTopic());
    }
}