package com.umesh.delivery_service.domain.delivery.orchestrator;

import com.umesh.delivery_service.domain.delivery.processor.DeliveryProcessor;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import com.umesh.shared.event.NotificationRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryOrchestrator {

    // private final DeliveryService deliveryService;
    private final DeliveryProcessor deliveryProcessor;

    public void process(NotificationRequestedEvent event) {

        deliveryProcessor.process(event);

    }

}