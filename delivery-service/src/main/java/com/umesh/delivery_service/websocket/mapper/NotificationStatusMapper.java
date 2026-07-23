package com.umesh.delivery_service.websocket.mapper;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.websocket.event.NotificationStatusEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationStatusMapper {

    public NotificationStatusEvent toEvent(

            Delivery delivery,

            String oldStatus,

            String newStatus,

            String message) {

        return NotificationStatusEvent.builder()

                .eventId(delivery.getEventId())

                .notificationId(delivery.getNotificationId())

                .deliveryId(delivery.getId())

                .oldStatus(oldStatus)

                .newStatus(newStatus)

                .channel(delivery.getChannel())

                .provider(delivery.getProvider())

                .message(message)

                .timestamp(LocalDateTime.now())

                .build();

    }

}