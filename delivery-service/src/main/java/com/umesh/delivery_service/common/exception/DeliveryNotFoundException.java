package com.umesh.delivery_service.common.exception;

public class DeliveryNotFoundException
        extends RuntimeException {

    public DeliveryNotFoundException(Long id) {

        super("Delivery not found with id: " + id);

    }
}