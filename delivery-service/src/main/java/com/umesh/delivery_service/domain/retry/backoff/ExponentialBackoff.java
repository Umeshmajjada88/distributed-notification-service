package com.umesh.delivery_service.domain.retry.backoff;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ExponentialBackoff {

    @Value("${notification.retry.retry-delay-ms}")
    private long initialDelay;

    public Duration nextDelay(int attempt) {

        long delay = initialDelay * (1L << attempt);

        return Duration.ofMillis(delay);

    }

}