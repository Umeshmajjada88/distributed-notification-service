package com.umesh.delivery_service.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    public UUID generateEventId() {
        return UUID.randomUUID();
    }

}