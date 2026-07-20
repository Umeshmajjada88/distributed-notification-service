package com.umesh.delivery_service.config.retry;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "notification.retry")
public class RetryProperties {

    /**
     * Maximum delivery attempts before marking a notification as permanently
     * failed.
     */
    private int maxAttempts = 3;

    /**
     * Delay (milliseconds) before scheduling the next retry.
     * We'll use this in the next sprint when we introduce delayed retries.
     */
    private long retryDelayMs = 5000L;
}