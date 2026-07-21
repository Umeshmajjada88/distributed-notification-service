package com.umesh.delivery_service.domain.deadletter.mapper;

import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterResponse;
import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeadLetterMapper {

    public DeadLetterResponse toResponse(DeadLetter deadLetter) {

        return DeadLetterResponse.builder()
                .id(deadLetter.getId())
                .deliveryId(deadLetter.getDeliveryId())
                .notificationId(deadLetter.getNotificationId())
                .eventId(deadLetter.getEventId())
                .channel(deadLetter.getChannel())
                .provider(deadLetter.getProvider())
                .attemptCount(deadLetter.getAttemptCount())
                .failureReason(deadLetter.getFailureReason())
                .status(deadLetter.getStatus())
                .build();

    }

    public List<DeadLetterResponse> toResponseList(
            List<DeadLetter> deadLetters) {

        return deadLetters.stream()
                .map(this::toResponse)
                .toList();

    }

}