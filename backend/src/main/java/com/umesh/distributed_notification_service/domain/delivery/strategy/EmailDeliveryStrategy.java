package com.umesh.distributed_notification_service.domain.delivery.strategy;

import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailDeliveryStrategy implements NotificationDeliveryStrategy {

    @Override
    public NotificationChannel getSupportedChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void deliver(NotificationEvent event) {

        throw new RuntimeException("Simulated email delivery failure");

    }

    // @Override
    // public void deliver(NotificationEvent event) {

    //     log.info("""

    //             ================ EMAIL =================

    //             Event Id      : {}
    //             Notification  : {}
    //             User Id       : {}
    //             Subject       : {}

    //             Email delivered successfully.

    //             =========================================
    //             """,
    //             event.getEventId(),
    //             event.getNotificationId(),
    //             event.getUserId(),
    //             event.getSubject());
    // }
}