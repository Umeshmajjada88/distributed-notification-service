package com.umesh.delivery_service.infrastructure.kafka.consumer;

import com.umesh.shared.event.NotificationDeadLetterEvent;
import com.umesh.shared.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadLetterConsumer {

    @KafkaListener(topics = KafkaTopics.NOTIFICATION_DLQ, 
        groupId = "${spring.kafka.consumer.group-id}", 
        containerFactory = "deadLetterKafkaListenerContainerFactory")
    public void consume(NotificationDeadLetterEvent event) {

        log.error("""

                ================= DEAD LETTER EVENT =================

                Delivery Id    : {}

                Notification Id: {}

                Event Id       : {}

                Channel        : {}

                Provider       : {}

                Attempts       : {}

                Failure Reason : {}

                =====================================================

                """,
                event.getDeliveryId(),
                event.getNotificationId(),
                event.getEventId(),
                event.getChannel(),
                event.getProvider(),
                event.getAttemptCount(),
                event.getFailureReason());

    }

}