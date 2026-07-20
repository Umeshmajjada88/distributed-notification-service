package com.umesh.delivery_service.domain.retry.policy;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeliveryRetryPolicy {

    @Value("${notification.retry.max-attempts}")
    private int maxAttempts;

    public boolean shouldRetry(Delivery delivery) {

        return delivery.getAttemptCount() < maxAttempts;

    }

}