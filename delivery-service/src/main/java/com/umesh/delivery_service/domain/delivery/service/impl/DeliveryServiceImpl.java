package com.umesh.delivery_service.domain.delivery.service.impl;

import com.umesh.delivery_service.common.exception.DeliveryNotFoundException;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.delivery_service.domain.delivery.mapper.DeliveryMapper;
import com.umesh.delivery_service.domain.delivery.repository.DeliveryRepository;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.shared.event.NotificationRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;

    private final DeliveryMapper mapper;

    @Override
    public Delivery createDelivery(NotificationRequestedEvent event) {

        if (repository.existsByEventId(event.getEventId())) {

            log.warn(
                    "Duplicate event received. eventId={}",
                    event.getEventId());

            return repository.findByEventId(event.getEventId())
                    .orElseThrow();
        }

        Delivery delivery = mapper.toEntity(event);

        Delivery saved = repository.save(delivery);

        log.info(
                "Delivery {} created successfully",
                saved.getId());

        return saved;
    }

    @Override
    public Delivery updateStatus(
            Long deliveryId,
            DeliveryStatus status) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(status);

        return repository.save(delivery);
    }

    @Override
    public Delivery markDelivered(
            Long deliveryId,
            String providerMessageId) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(DeliveryStatus.DELIVERED);

        delivery.setDeliveredAt(LocalDateTime.now());

        delivery.setProviderMessageId(providerMessageId);

        return repository.save(delivery);
    }

    @Override
    public Delivery markFailed(
            Long deliveryId,
            String reason) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(DeliveryStatus.FAILED);

        delivery.setFailureReason(reason);

        return repository.save(delivery);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findByEventId(UUID eventId) {

        return repository.findByEventId(eventId);

    }

    private Delivery getDelivery(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));

    }
}