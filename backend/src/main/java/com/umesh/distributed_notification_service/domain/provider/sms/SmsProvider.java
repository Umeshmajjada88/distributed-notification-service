package com.umesh.distributed_notification_service.domain.provider.sms;

import com.umesh.distributed_notification_service.domain.provider.sms.dto.SmsRequest;

public interface SmsProvider {

    void send(SmsRequest request);

}