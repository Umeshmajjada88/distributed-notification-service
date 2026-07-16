package com.umesh.distributed_notification_service.domain.delivery.strategy;

import com.umesh.distributed_notification_service.domain.provider.email.dto.EmailRequest;
import com.umesh.distributed_notification_service.domain.provider.email.EmailProvider;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailDeliveryStrategy implements NotificationDeliveryStrategy {

    private final EmailProvider emailProvider;

    @Value("${notification.email.default-recipient}")
    private String defaultRecipient;

    @Override
    public NotificationChannel getSupportedChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void deliver(NotificationEvent event) {

        EmailRequest request = EmailRequest.builder()
                .to(defaultRecipient)
                .subject(event.getSubject())
                .body(event.getMessage())
                .build();

        emailProvider.send(request);

        log.info(
                "Email notification {} sent successfully.",
                event.getNotificationId());

    }
}