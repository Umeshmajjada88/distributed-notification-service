package com.umesh.distributed_notification_service.domain.provider.fallback;

import com.umesh.distributed_notification_service.domain.provider.email.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationFallbackService {

    public void emailFallback(
            EmailRequest request,
            Exception exception) {

        log.error(
                "Email provider unavailable. Fallback executed. recipient={}, reason={}",
                request.getTo(),
                exception.getMessage());

    }

}