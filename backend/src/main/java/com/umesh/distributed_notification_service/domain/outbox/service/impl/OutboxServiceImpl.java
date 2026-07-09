package com.umesh.distributed_notification_service.domain.outbox.service.impl;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.repository.OutboxRepository;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;

    @Override
    public OutboxEvent save(OutboxEvent outboxEvent) {
        return outboxRepository.save(outboxEvent);
    }
}