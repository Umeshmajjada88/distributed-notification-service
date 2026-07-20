package com.umesh.distributed_notification_service.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonSerializer {

    private final ObjectMapper objectMapper;

    public String serialize(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(
                    "Failed to serialize object.", ex);
        }

    }

    public <T> T deserialize(String json, Class<T> clazz) {

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(
                    "Failed to deserialize JSON.", ex);
        }

    }

}