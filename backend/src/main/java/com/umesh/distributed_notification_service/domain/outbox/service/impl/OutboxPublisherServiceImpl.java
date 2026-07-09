package com.umesh.distributed_notification_service.domain.outbox.service.impl;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.enums.OutboxStatus;
import com.umesh.distributed_notification_service.domain.outbox.publisher.OutboxEventPublisher;
import com.umesh.distributed_notification_service.domain.outbox.repository.OutboxRepository;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisherServiceImpl
        implements OutboxPublisherService {

    private final OutboxRepository outboxRepository;

    private final OutboxEventPublisher eventPublisher;

    @Override
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEvent> events = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc(
                OutboxStatus.PENDING);

        for (OutboxEvent event : events) {

            try {

                eventPublisher.publish(event);

                event.setStatus(OutboxStatus.PUBLISHED);

                event.setPublishedAt(LocalDateTime.now());

                outboxRepository.save(event);

            } catch (Exception ex) {

                log.error(
                        "Failed to publish Outbox Event {}",
                        event.getId(),
                        ex);

            }

        }

    }

}