package com.umesh.distributed_notification_service.domain.outbox.mapper;

import com.umesh.distributed_notification_service.common.serialization.JsonSerializer;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.enums.OutboxStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMapper {

    private final JsonSerializer jsonSerializer;

    public OutboxEvent toOutboxEvent(
            String aggregateType,
            String aggregateId,
            String eventType,
            NotificationEvent event) {

        return OutboxEvent.builder()
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .eventType(eventType)
                .payload(jsonSerializer.serialize(event))
                .status(OutboxStatus.PENDING)
                .retryCount(0)
                .build();
    }
}