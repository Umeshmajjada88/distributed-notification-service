package com.umesh.distributed_notification_service.domain.outbox.mapper;

import com.umesh.distributed_notification_service.common.serialization.JsonSerializer;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.enums.OutboxStatus;
import com.umesh.distributed_notification_service.infrastructure.kafka.resolver.EventTopicResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMapper {

    private final JsonSerializer jsonSerializer;
    private final EventTopicResolver topicResolver;

    public OutboxEvent toOutboxEvent(
            AggregateType aggregateType,
            String aggregateId,
            NotificationEventType eventType,
            Object payload) {

        return OutboxEvent.builder()
                .aggregateType(aggregateType.name())
                .aggregateId(aggregateId)
                .eventType(eventType.name())
                .topic(topicResolver.resolve(eventType))
                .payload(jsonSerializer.serialize(payload))
                .status(OutboxStatus.PENDING)
                .retryCount(0)
                .build();
    }
}