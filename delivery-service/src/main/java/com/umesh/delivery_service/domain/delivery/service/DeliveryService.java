package com.umesh.delivery_service.domain.delivery.service;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.shared.event.NotificationRequestedEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryService {

    Delivery createDelivery(NotificationRequestedEvent event);

    Delivery updateStatus(Long deliveryId, DeliveryStatus status);

    Delivery markDelivered(Long deliveryId, String providerMessageId);

    Delivery markFailed(Long deliveryId, String reason);

    Delivery incrementAttemptCount(Long deliveryId);

    Delivery scheduleNextRetry(Long deliveryId);

    Optional<Delivery> findByEventId(UUID eventId);

    Delivery save(Delivery delivery);

    Optional<Delivery> findById(Long deliveryId);

    List<Delivery> findReadyForRetry(LocalDateTime now);

}