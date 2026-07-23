package com.umesh.delivery_service.domain.delivery.processor;

import com.umesh.delivery_service.common.exception.ResourceNotFoundException;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.delivery_service.domain.provider.factory.ProviderFactory;
import com.umesh.delivery_service.domain.provider.interfaces.NotificationProvider;
import com.umesh.delivery_service.websocket.service.NotificationStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryProcessor {

    private final DeliveryService deliveryService;

    private final ProviderFactory providerFactory;

    private final NotificationStatusService notificationStatusService;

    public void execute(Delivery delivery) {

            /*
             * PENDING -> IN_PROGRESS
             */
            String oldStatus = delivery.getStatus().name();

            deliveryService.updateStatus(
                            delivery.getId(),
                            DeliveryStatus.IN_PROGRESS);

            delivery = deliveryService.findById(delivery.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

            notificationStatusService.publishStatus(
                            delivery,
                            oldStatus,
                            delivery.getStatus().name(),
                            "Delivery started");

            try {

                    NotificationProvider provider = providerFactory.getProvider(
                                    delivery.getProvider());

                    provider.send(delivery);

                    /*
                     * IN_PROGRESS -> DELIVERED
                     */
                    oldStatus = delivery.getStatus().name();

                    deliveryService.markDelivered(
                                    delivery.getId(),
                                    "LOCAL-DEMO-" + delivery.getId());

                    delivery = deliveryService.findById(delivery.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

                    notificationStatusService.publishStatus(
                                    delivery,
                                    oldStatus,
                                    delivery.getStatus().name(),
                                    "Notification delivered successfully");

                    log.info(
                                    "Delivery {} completed successfully",
                                    delivery.getId());

            } catch (Exception ex) {

                    log.error(
                                    "Delivery {} failed",
                                    delivery.getId(),
                                    ex);

                    /*
                     * IN_PROGRESS -> FAILED
                     */
                    oldStatus = delivery.getStatus().name();

                    deliveryService.markFailed(
                                    delivery.getId(),
                                    ex.getMessage());

                    delivery = deliveryService.findById(delivery.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

                    notificationStatusService.publishStatus(
                                    delivery,
                                    oldStatus,
                                    delivery.getStatus().name(),
                                    "Delivery failed: " + ex.getMessage());

            }

    }

}