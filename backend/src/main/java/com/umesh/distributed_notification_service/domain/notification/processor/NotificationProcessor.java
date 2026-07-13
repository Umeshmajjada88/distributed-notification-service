        package com.umesh.distributed_notification_service.domain.notification.processor;

        import com.umesh.distributed_notification_service.domain.delivery.service.DeliveryService;
        import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.stereotype.Component;

        @Slf4j
        @Component
        @RequiredArgsConstructor
        public class NotificationProcessor {

        private final DeliveryService deliveryService;

        public void process(NotificationEvent event) {

                log.info(
                        "Processing notification event. eventId={}, channel={}",
                        event.getEventId(),
                        event.getChannel());

                deliveryService.deliver(event);

                log.info(
                        "Notification event processed successfully. eventId={}",
                        event.getEventId());

                log.info(
                        "Processing notification {}",
                        event.getNotificationId());
        }
        }