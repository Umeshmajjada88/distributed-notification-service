package com.umesh.delivery_service.domain.deadletter.dto.response;

import com.umesh.delivery_service.domain.deadletter.enums.DeadLetterStatus;
import com.umesh.shared.types.NotificationChannel;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;

import java.util.UUID;

@Value
@Builder
public class DeadLetterResponse {

    Long id;

    Long deliveryId;

    Long notificationId;

    UUID eventId;

    NotificationChannel channel;

    String provider;

    Integer attemptCount;

    String failureReason;

    DeadLetterStatus status;

    LocalDateTime createdAt;

}