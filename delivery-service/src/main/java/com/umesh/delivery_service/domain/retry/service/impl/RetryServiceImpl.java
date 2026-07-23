package com.umesh.delivery_service.domain.retry.service.impl;

import com.umesh.delivery_service.domain.deadletter.service.DeadLetterService;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.delivery_service.domain.delivery.processor.DeliveryProcessor;
import com.umesh.delivery_service.domain.delivery.repository.DeliveryRepository;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.delivery_service.domain.retry.backoff.ExponentialBackoff;
import com.umesh.delivery_service.domain.retry.policy.DeliveryRetryPolicy;
import com.umesh.delivery_service.domain.retry.service.RetryService;
import com.umesh.delivery_service.websocket.service.NotificationStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryServiceImpl implements RetryService {

    private final DeliveryRetryPolicy retryPolicy;

    private final ExponentialBackoff backoff;

    private final DeliveryService deliveryService;

    private final DeliveryProcessor deliveryProcessor;

    private final DeadLetterService deadLetterService;

    private final NotificationStatusService notificationStatusService;



    @Override
    public void scheduleRetry(Delivery delivery) {

        if (!retryPolicy.shouldRetry(delivery)) {

            deadLetterService.saveFailedDelivery(delivery);

            log.warn(
                    "Delivery {} moved to Dead Letter Queue",
                    delivery.getId());

            return;
        }

        delivery.setAttemptCount(
                delivery.getAttemptCount() + 1);

        delivery.setNextRetryAt(
                LocalDateTime.now().plus(
                        backoff.nextDelay(
                                delivery.getAttemptCount())));

        delivery.setStatus(
                DeliveryStatus.RETRY_PENDING);

        deliveryService.save(delivery);

        String oldStatus = DeliveryStatus.FAILED.name();

        notificationStatusService.publishStatus(
                        delivery,
                        oldStatus,
                        delivery.getStatus().name(),
                        "Retry scheduled (Attempt "
                                        + delivery.getAttemptCount()
                                        + ")");


        log.info(
                "Retry scheduled. deliveryId={}, attempt={}, nextRetryAt={}",
                delivery.getId(),
                delivery.getAttemptCount(),
                delivery.getNextRetryAt());
    }


    

    @Override
    public void publishReadyRetries() {

        List<Delivery> deliveries = deliveryService.findReadyForRetry(
                LocalDateTime.now());

        for (Delivery delivery : deliveries) {

            log.info(
                    "Retrying delivery {}",
                    delivery.getId());

            deliveryProcessor.execute(delivery);

        }

    }
}