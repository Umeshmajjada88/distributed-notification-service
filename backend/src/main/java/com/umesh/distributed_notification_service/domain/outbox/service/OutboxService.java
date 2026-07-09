package com.umesh.distributed_notification_service.domain.outbox.service;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;

public interface OutboxService {

    OutboxEvent save(OutboxEvent outboxEvent);

}