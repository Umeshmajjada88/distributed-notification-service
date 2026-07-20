package com.umesh.distributed_notification_service.infrastructure.metrics;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class NotificationMetrics {

    private final MeterRegistry meterRegistry;

    public NotificationMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementCreated(NotificationChannel channel) {

        meterRegistry.counter(
                "notification_created_total",
                "channel",
                channel.name())
                .increment();

    }

    public void incrementSent(NotificationChannel channel) {

        meterRegistry.counter(
                "notification_sent_total",
                "channel",
                channel.name())
                .increment();

    }

    public void incrementFailed(NotificationChannel channel) {

        meterRegistry.counter(
                "notification_failed_total",
                "channel",
                channel.name())
                .increment();

    }

    public void incrementRetry(NotificationChannel channel) {

        meterRegistry.counter(
                "notification_retry_total",
                "channel",
                channel.name())
                .increment();

    }

    public void incrementDlq(NotificationChannel channel) {

        meterRegistry.counter(
                "notification_dlq_total",
                "channel",
                channel.name())
                .increment();

    }

}