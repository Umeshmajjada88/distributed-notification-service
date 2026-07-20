package com.umesh.delivery_service.domain.provider.interfaces;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;

public interface NotificationProvider {

    DeliveryProvider getProvider();

    void send(Delivery delivery);

}