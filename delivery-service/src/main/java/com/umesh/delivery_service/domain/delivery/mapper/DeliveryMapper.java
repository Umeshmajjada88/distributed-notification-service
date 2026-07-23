package com.umesh.delivery_service.domain.delivery.mapper;

import com.umesh.delivery_service.domain.delivery.dto.response.DeliveryResponse;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.shared.event.NotificationRequestedEvent;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public Delivery toEntity(NotificationRequestedEvent event) {

        return Delivery.builder()
                .notificationId(event.getNotificationId())
                .eventId(event.getEventId())
                .userId(event.getUserId())
                .channel(event.getChannel())
                .subject(event.getSubject())
                .message(event.getMessage())
                .provider(DeliveryProvider.SMTP)
                .status(DeliveryStatus.PENDING)
                .attemptCount(0)
                .build();
    }

    public DeliveryResponse toResponse(Delivery delivery) {

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .notificationId(delivery.getNotificationId())
                .eventId(delivery.getEventId())
                .provider(delivery.getProvider().name())
                .status(delivery.getStatus())
                .attemptCount(delivery.getAttemptCount())
                .failureReason(delivery.getFailureReason())
                .nextRetryAt(delivery.getNextRetryAt())
                .createdAt(delivery.getCreatedAt())
                .updatedAt(delivery.getUpdatedAt())
                .build();

    }
}