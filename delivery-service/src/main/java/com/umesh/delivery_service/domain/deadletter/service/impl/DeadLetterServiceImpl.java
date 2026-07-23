package com.umesh.delivery_service.domain.deadletter.service.impl;

import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterResponse;
import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterStatisticsResponse;
import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import com.umesh.delivery_service.domain.deadletter.enums.DeadLetterStatus;
import com.umesh.delivery_service.domain.deadletter.mapper.DeadLetterMapper;
import com.umesh.delivery_service.domain.deadletter.repository.DeadLetterRepository;
import com.umesh.delivery_service.domain.deadletter.service.DeadLetterService;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.processor.DeliveryProcessor;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.delivery_service.infrastructure.kafka.publisher.DeadLetterEventPublisher;
import com.umesh.delivery_service.websocket.service.NotificationStatusService;
import com.umesh.shared.event.NotificationDeadLetterEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeadLetterServiceImpl implements DeadLetterService {

    private final DeadLetterRepository deadLetterRepository;

    private final DeadLetterEventPublisher deadLetterEventPublisher;

    private final DeliveryService deliveryService;

    private final DeliveryProcessor deliveryProcessor;

    private final NotificationStatusService notificationStatusService;

    private final DeadLetterMapper deadLetterMapper;

    @Override
    public DeadLetter saveFailedDelivery(Delivery delivery) {

        DeadLetter deadLetter = DeadLetter.builder()
                .deliveryId(delivery.getId())
                .notificationId(delivery.getNotificationId())
                .eventId(delivery.getEventId())
                .channel(delivery.getChannel())
                .provider(delivery.getProvider().name())
                .attemptCount(delivery.getAttemptCount())
                .failureReason(delivery.getFailureReason())
                .payload(delivery.getMessage())
                .status(DeadLetterStatus.PENDING)
                .build();

        DeadLetter saved = deadLetterRepository.save(deadLetter);

        notificationStatusService.publishStatus(
                        delivery,
                        delivery.getStatus().name(),
                        delivery.getStatus().name(),
                        "Delivery moved to Dead Letter Queue");

        NotificationDeadLetterEvent event =
                NotificationDeadLetterEvent.builder()
                        .eventId(saved.getEventId())
                        .deliveryId(saved.getDeliveryId())
                        .notificationId(saved.getNotificationId())
                        .channel(saved.getChannel())
                        .provider(saved.getProvider())
                        .attemptCount(saved.getAttemptCount())
                        .failureReason(saved.getFailureReason())
                        .build();

        deadLetterEventPublisher.publish(event);

        return saved;

    }

    @Override
    public Optional<DeadLetter> findById(Long id) {

        return deadLetterRepository.findById(id);

    }

    @Override
    public List<DeadLetter> findAll() {

        return deadLetterRepository.findAll();

    }

    @Override
    @Transactional
    public DeadLetter replay(Long deadLetterId) {

        DeadLetter deadLetter = deadLetterRepository.findById(deadLetterId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dead Letter not found"));

        Delivery delivery = deliveryService.findById(
                deadLetter.getDeliveryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found"));

        deliveryProcessor.execute(delivery);

        deadLetter.setStatus(
                DeadLetterStatus.REPLAYED);

        DeadLetter updated = deadLetterRepository.save(deadLetter);

        notificationStatusService.publishStatus(
                        delivery,
                        delivery.getStatus().name(),
                        delivery.getStatus().name(),
                        "Replay requested");

        return updated;

    }

    @Override
    public List<DeadLetter> findPending() {

        return deadLetterRepository.findByStatus(
                DeadLetterStatus.PENDING);

    }

    @Override
        public DeadLetterStatisticsResponse getStatistics() {

        return DeadLetterStatisticsResponse.builder()
                .total(deadLetterRepository.count())
                .pending(deadLetterRepository.countByStatus(DeadLetterStatus.PENDING))
                .replayed(deadLetterRepository.countByStatus(DeadLetterStatus.REPLAYED))
                .discarded(deadLetterRepository.countByStatus(DeadLetterStatus.DISCARDED))
                .build();

        }

        @Override
        public List<DeadLetterResponse> getAllDeadLetters() {

        return deadLetterRepository.findAll()
                .stream()
                .map(deadLetterMapper::toResponse)
                .toList();

        }

}