package com.umesh.distributed_notification_service.domain.provider.email;

import com.umesh.distributed_notification_service.domain.provider.email.dto.EmailRequest;

public interface EmailProvider {

    void send(EmailRequest request);

}