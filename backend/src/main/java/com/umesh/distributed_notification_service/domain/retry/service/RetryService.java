package com.umesh.distributed_notification_service.domain.retry.service;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;

public interface RetryService {

    void retry(Notification notification);

}