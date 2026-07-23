package com.umesh.delivery_service.websocket.publisher.impl;

import com.umesh.delivery_service.websocket.event.NotificationStatusEvent;
import com.umesh.delivery_service.websocket.publisher.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompWebSocketPublisher
                implements WebSocketPublisher {

        private final SimpMessagingTemplate messagingTemplate;

        @Override
        public void publish(NotificationStatusEvent event) {

                log.info(
                                "Publishing WebSocket event: {}",
                                event);

                messagingTemplate.convertAndSend(
                                "/topic/notifications",
                                event);

                log.info(
                                "Published WebSocket event for notification {} [{} -> {}]",
                                event.getNotificationId(),
                                event.getOldStatus(),
                                event.getNewStatus());

        }

}