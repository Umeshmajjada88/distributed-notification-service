package com.umesh.distributed_notification_service.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class NotificationMetrics {

    private final Counter notificationCreatedCounter;
    private final Counter notificationSentCounter;
    private final Counter notificationFailedCounter;
    private final Counter notificationRetryCounter;
    private final Counter notificationDlqCounter;

    public NotificationMetrics(MeterRegistry meterRegistry) {

        notificationCreatedCounter = Counter.builder("notification_created_total")
                .description("Total notifications created")
                .register(meterRegistry);

        notificationSentCounter = Counter.builder("notification_sent_total")
                .description("Total notifications sent successfully")
                .register(meterRegistry);

        notificationFailedCounter = Counter.builder("notification_failed_total")
                .description("Total notification failures")
                .register(meterRegistry);

        notificationRetryCounter = Counter.builder("notification_retry_total")
                .description("Total notification retries")
                .register(meterRegistry);

        notificationDlqCounter = Counter.builder("notification_dlq_total")
                .description("Total notifications routed to DLQ")
                .register(meterRegistry);
    }

    public void incrementCreated() {
        notificationCreatedCounter.increment();
    }

    public void incrementSent() {
        notificationSentCounter.increment();
    }

    public void incrementFailed() {
        notificationFailedCounter.increment();
    }

    public void incrementRetry() {
        notificationRetryCounter.increment();
    }

    public void incrementDlq() {
        notificationDlqCounter.increment();
    }
}