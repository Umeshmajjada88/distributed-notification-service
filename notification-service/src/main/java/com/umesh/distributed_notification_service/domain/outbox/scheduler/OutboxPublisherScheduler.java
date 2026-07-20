package com.umesh.distributed_notification_service.domain.outbox.scheduler;

import com.umesh.distributed_notification_service.domain.outbox.service.OutboxPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherScheduler {

    private final OutboxPublisherService publisherService;

    @Scheduled(fixedDelay = 5000)
    public void publishEvents() {

        log.info("Scanning Outbox for pending events...");

        publisherService.publishPendingEvents();

    }

}