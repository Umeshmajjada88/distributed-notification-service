package com.umesh.delivery_service.websocket.service;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;

public interface NotificationStatusService {

    void publishStatus(

            Delivery delivery,

            String oldStatus,

            String newStatus,

            String message);

}