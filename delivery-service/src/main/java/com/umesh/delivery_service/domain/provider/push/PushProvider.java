package com.umesh.delivery_service.domain.provider.push;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;
import com.umesh.delivery_service.domain.provider.interfaces.NotificationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushProvider implements NotificationProvider {

    @Override
    public DeliveryProvider getProvider() {
        return DeliveryProvider.FIREBASE;
    }

    @Override
    public void send(Delivery delivery) {

        log.info(
                "Push Provider selected for Notification {}",
                delivery.getNotificationId());

    }
}