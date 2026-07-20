package com.umesh.delivery_service.infrastructure.email.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailRequest {

    String to;

    String subject;

    String body;

}