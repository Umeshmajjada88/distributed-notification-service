package com.umesh.delivery_service.domain.delivery.dto.response;

import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DeliveryResponse {

    private Long id;

    private UUID eventId;

    private Long notificationId;

    private String provider;

    private DeliveryStatus status;

    private Integer attemptCount;

    private String failureReason;

    private LocalDateTime nextRetryAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}