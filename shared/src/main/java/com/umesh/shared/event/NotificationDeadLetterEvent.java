package com.umesh.shared.event;

import com.umesh.shared.types.NotificationChannel;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder
public class NotificationDeadLetterEvent implements Serializable {

    UUID eventId;

    Long deliveryId;

    Long notificationId;

    NotificationChannel channel;

    String provider;

    Integer attemptCount;

    String failureReason;

}