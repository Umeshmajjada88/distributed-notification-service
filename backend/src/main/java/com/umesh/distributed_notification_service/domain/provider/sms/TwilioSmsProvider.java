package com.umesh.distributed_notification_service.domain.provider.sms;

import com.umesh.distributed_notification_service.domain.provider.sms.dto.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwilioSmsProvider implements SmsProvider {

    @Override
    public void send(SmsRequest request) {

        log.info("""

                ================ SMS ================

                To      : {}

                Message : {}

                SMS provider invoked.

                =====================================
                """,
                request.getTo(),
                request.getMessage());

    }

}