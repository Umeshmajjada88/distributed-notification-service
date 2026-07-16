package com.umesh.distributed_notification_service.domain.delivery.strategy;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.provider.sms.SmsProvider;
import com.umesh.distributed_notification_service.domain.provider.sms.dto.SmsRequest;
import com.umesh.distributed_notification_service.domain.provider.sms.mapper.SmsRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsDeliveryStrategy implements NotificationDeliveryStrategy {

    private final SmsProvider smsProvider;

    private final SmsRequestMapper smsRequestMapper;

    @Override
    public NotificationChannel getSupportedChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public void deliver(NotificationEvent event) {

        SmsRequest request = smsRequestMapper.toRequest(event);

        smsProvider.send(request);

        log.info(
                "SMS notification {} delivered.",
                event.getNotificationId());

    }

}