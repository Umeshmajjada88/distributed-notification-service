package com.umesh.distributed_notification_service.common.exception;

public class BadRequestException extends NotificationServiceException {

    public BadRequestException(String message) {
        super(message);
    }

}