package com.umesh.delivery_service.domain.delivery.processor;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.delivery_service.domain.provider.factory.ProviderFactory;
import com.umesh.delivery_service.domain.provider.interfaces.NotificationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryProcessor {

    private final DeliveryService deliveryService;

    private final ProviderFactory providerFactory;

    public void execute(Delivery delivery) {

        deliveryService.updateStatus(
                delivery.getId(),
                DeliveryStatus.IN_PROGRESS);

        try {

            NotificationProvider provider = providerFactory.getProvider(
                    delivery.getProvider());

            provider.send(delivery);

            deliveryService.markDelivered(
                    delivery.getId(),
                    "LOCAL-DEMO-" + delivery.getId());

            log.info(
                    "Delivery {} completed successfully",
                    delivery.getId());

        } catch (Exception ex) {

            log.error(
                    "Delivery {} failed",
                    delivery.getId(),
                    ex);

            deliveryService.markFailed(
                    delivery.getId(),
                    ex.getMessage());

        }

    }

}