package com.umesh.distributed_notification_service.domain.retry.policy;

import com.umesh.distributed_notification_service.config.retry.RetryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RetryPolicy {

    private final RetryProperties retryProperties;

    public boolean shouldRetry(int retryCount) {

        return retryCount <= retryProperties.getMaxAttempts();

    }

    public LocalDateTime nextRetryTime(int retryCount) {

        long delayMillis = retryProperties.getRetryDelayMs()
                * (1L << (retryCount - 1));

        return LocalDateTime.now()
                .plus(Duration.ofMillis(delayMillis));

    }

}