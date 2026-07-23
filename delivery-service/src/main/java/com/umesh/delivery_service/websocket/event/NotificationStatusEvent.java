package com.umesh.delivery_service.websocket.event;

import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;
import com.umesh.shared.types.NotificationChannel;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class NotificationStatusEvent {

    UUID eventId;

    Long notificationId;

    Long deliveryId;

    String oldStatus;

    String newStatus;

    NotificationChannel channel;

    DeliveryProvider provider;

    String message;

    LocalDateTime timestamp;

}