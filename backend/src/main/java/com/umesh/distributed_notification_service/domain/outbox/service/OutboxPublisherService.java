package com.umesh.distributed_notification_service.domain.outbox.service;

public interface OutboxPublisherService {

    void publishPendingEvents();

}