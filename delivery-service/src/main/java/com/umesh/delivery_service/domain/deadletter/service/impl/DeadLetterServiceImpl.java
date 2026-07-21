package com.umesh.delivery_service.domain.deadletter.service.impl;

import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import com.umesh.delivery_service.domain.deadletter.enums.DeadLetterStatus;
import com.umesh.delivery_service.domain.deadletter.repository.DeadLetterRepository;
import com.umesh.delivery_service.domain.deadletter.service.DeadLetterService;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.processor.DeliveryProcessor;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.delivery_service.infrastructure.kafka.publisher.DeadLetterEventPublisher;
import com.umesh.shared.event.NotificationDeadLetterEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeadLetterServiceImpl implements DeadLetterService {

    private final DeadLetterRepository repository;

    private final DeadLetterEventPublisher deadLetterEventPublisher;

    private final DeliveryService deliveryService;

    private final DeliveryProcessor deliveryProcessor;

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

        DeadLetter saved = repository.save(deadLetter);

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

        return repository.findById(id);

    }

    @Override
    public List<DeadLetter> findAll() {

        return repository.findAll();

    }

    @Override
    @Transactional
    public DeadLetter replay(Long deadLetterId) {

        DeadLetter deadLetter = repository.findById(deadLetterId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dead Letter not found"));

        Delivery delivery = deliveryService.findById(
                deadLetter.getDeliveryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found"));

        deliveryProcessor.execute(delivery);

        deadLetter.setStatus(
                DeadLetterStatus.REPLAYED);

        DeadLetter updated = repository.save(deadLetter);

        return updated;

    }

    @Override
    public List<DeadLetter> findPending() {

        return repository.findByStatus(
                DeadLetterStatus.PENDING);

    }

}