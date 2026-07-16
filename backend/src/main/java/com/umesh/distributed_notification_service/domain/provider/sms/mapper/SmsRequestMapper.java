package com.umesh.distributed_notification_service.domain.provider.sms.mapper;

import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.provider.sms.dto.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsRequestMapper {

    @Value("${notification.sms.default-recipient}")
    private String defaultRecipient;

    public SmsRequest toRequest(NotificationEvent event) {

        return SmsRequest.builder()
                .to(defaultRecipient)
                .message(event.getMessage())
                .build();

    }

}