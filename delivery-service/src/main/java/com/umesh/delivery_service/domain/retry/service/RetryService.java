package com.umesh.delivery_service.domain.retry.service;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.shared.event.DeliveryRetryEvent;

public interface RetryService {

    void scheduleRetry(Delivery delivery);

    void publishReadyRetries();

}