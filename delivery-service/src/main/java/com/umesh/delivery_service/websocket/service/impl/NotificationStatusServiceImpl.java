package com.umesh.delivery_service.websocket.service.impl;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.websocket.event.NotificationStatusEvent;
import com.umesh.delivery_service.websocket.mapper.NotificationStatusMapper;
import com.umesh.delivery_service.websocket.publisher.WebSocketPublisher;
import com.umesh.delivery_service.websocket.service.NotificationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationStatusServiceImpl
        implements NotificationStatusService {

    private final WebSocketPublisher webSocketPublisher;
    private final NotificationStatusMapper notificationStatusMapper;

    @Override
    public void publishStatus(

            Delivery delivery,

            String oldStatus,

            String newStatus,

            String message) {

        NotificationStatusEvent event = notificationStatusMapper.toEvent(

                delivery,

                oldStatus,

                newStatus,

                message);

        webSocketPublisher.publish(event);

    }

}