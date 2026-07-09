package com.umesh.distributed_notification_service.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JacksonJsonSerializer implements JsonSerializer {

    private final ObjectMapper objectMapper;

    @Override
    public String serialize(Object object) {

        try {
            return objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException ex) {

            throw new IllegalStateException(
                    "Failed to serialize object.", ex);
        }
    }
}