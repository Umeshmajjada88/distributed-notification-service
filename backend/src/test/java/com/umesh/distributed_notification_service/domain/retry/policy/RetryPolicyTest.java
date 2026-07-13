package com.umesh.distributed_notification_service.domain.retry.policy;

import com.umesh.distributed_notification_service.config.retry.RetryProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RetryPolicyTest {

    private RetryPolicy retryPolicy;

    @BeforeEach
    void setUp() {

        RetryProperties properties = new RetryProperties();
        properties.setMaxAttempts(3);
        properties.setRetryDelayMs(5000);

        retryPolicy = new RetryPolicy(properties);
    }

    @Test
    void shouldAllowRetryWhenBelowMaximumAttempts() {

        assertTrue(retryPolicy.shouldRetry(1));
        assertTrue(retryPolicy.shouldRetry(2));
        assertTrue(retryPolicy.shouldRetry(3));

    }

    @Test
    void shouldRejectRetryWhenMaximumAttemptsExceeded() {

        assertFalse(retryPolicy.shouldRetry(4));

    }

    @Test
    void shouldCalculateExponentialBackoffForFirstRetry() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime retry = retryPolicy.nextRetryTime(1);

        Duration duration = Duration.between(now, retry);

        assertTrue(duration.toMillis() >= 5000);

    }

    @Test
    void shouldCalculateExponentialBackoffForSecondRetry() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime retry = retryPolicy.nextRetryTime(2);

        Duration duration = Duration.between(now, retry);

        assertTrue(duration.toMillis() >= 10000);

    }

    @Test
    void shouldCalculateExponentialBackoffForThirdRetry() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime retry = retryPolicy.nextRetryTime(3);

        Duration duration = Duration.between(now, retry);

        assertTrue(duration.toMillis() >= 20000);

    }

}