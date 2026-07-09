package com.umesh.distributed_notification_service.domain.outbox.publisher;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;

public interface OutboxEventPublisher {

    void publish(OutboxEvent event);

}