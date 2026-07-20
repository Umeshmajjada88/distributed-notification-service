package com.umesh.delivery_service.common.exception;

public class BadRequestException extends NotificationServiceException {

    public BadRequestException(String message) {
        super(message);
    }

}