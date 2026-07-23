package com.umesh.delivery_service.domain.delivery.service.impl;

import com.umesh.delivery_service.common.exception.DeliveryNotFoundException;
import com.umesh.delivery_service.domain.delivery.dto.response.DeliveryResponse;
import com.umesh.delivery_service.domain.delivery.dto.response.DeliveryStatisticsResponse;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    

    private final DeliveryMapper deliveryMapper;

    @Override
    public Delivery createDelivery(NotificationRequestedEvent event) {

        if (deliveryRepository.existsByEventId(event.getEventId())) {

            log.warn(
                    "Duplicate event received. eventId={}",
                    event.getEventId());

            return deliveryRepository.findByEventId(event.getEventId())
                    .orElseThrow();
        }

        Delivery delivery = deliveryMapper.toEntity(event);

        Delivery saved = deliveryRepository.save(delivery);

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

        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery markDelivered(
            Long deliveryId,
            String providerMessageId) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(DeliveryStatus.DELIVERED);

        delivery.setDeliveredAt(LocalDateTime.now());

        delivery.setProviderMessageId(providerMessageId);

        return deliveryRepository   .save(delivery);
    }

    @Override
    public Delivery markFailed(
            Long deliveryId,
            String reason) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(DeliveryStatus.FAILED);

        delivery.setFailureReason(reason);

        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findByEventId(UUID eventId) {

        return deliveryRepository.findByEventId(eventId);

    }

    private Delivery getDelivery(Long id) {

        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));

    }

    @Override
    public Delivery incrementAttemptCount(Long deliveryId) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setAttemptCount(
                delivery.getAttemptCount() + 1);

        return deliveryRepository.save(delivery);

    }

    @Override
    public Delivery scheduleNextRetry(Long deliveryId) {

        Delivery delivery = getDelivery(deliveryId);

        delivery.setStatus(DeliveryStatus.PENDING);

        return deliveryRepository.save(delivery);

    }

    @Override
    public Delivery save(Delivery delivery) {

        return deliveryRepository.save(delivery);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findById(Long deliveryId) {

        return deliveryRepository.findById(deliveryId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> findReadyForRetry(LocalDateTime now) {

        return deliveryRepository  
                .findTop100ByStatusAndNextRetryAtLessThanEqualOrderByNextRetryAtAsc(
                        DeliveryStatus.RETRY_PENDING,
                        now);

    }

    @Override
    public DeliveryStatisticsResponse getStatistics() {

        return DeliveryStatisticsResponse.builder()
                .total(deliveryRepository.count())
                .pending(deliveryRepository.countByStatus(DeliveryStatus.PENDING))
                .inProgress(deliveryRepository.countByStatus(DeliveryStatus.IN_PROGRESS))
                .retryPending(deliveryRepository.countByStatus(DeliveryStatus.RETRY_PENDING))
                .delivered(deliveryRepository.countByStatus(DeliveryStatus.DELIVERED))
                .failed(deliveryRepository.countByStatus(DeliveryStatus.FAILED))
                .build();

    }

    @Override
    public List<DeliveryResponse> getAllDeliveries() {

        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toResponse)
                .toList();

    }
}