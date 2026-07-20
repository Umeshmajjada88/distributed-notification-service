package com.umesh.shared.event;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder
public class DeliveryRetryEvent
        implements Serializable {

    UUID eventId;

    Long deliveryId;

    int attempt;

}