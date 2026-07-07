package com.umesh.distributed_notification_service.common.exception;

public class ResourceNotFoundException extends NotificationServiceException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}