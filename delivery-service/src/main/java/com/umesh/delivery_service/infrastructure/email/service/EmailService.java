package com.umesh.delivery_service.infrastructure.email.service;

import com.umesh.delivery_service.infrastructure.email.dto.EmailRequest;

public interface EmailService {

    void send(EmailRequest request);

}