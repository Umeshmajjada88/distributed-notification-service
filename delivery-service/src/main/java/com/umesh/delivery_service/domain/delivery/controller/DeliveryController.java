package com.umesh.delivery_service.domain.delivery.controller;

import com.umesh.delivery_service.domain.delivery.dto.response.DeliveryResponse;
import com.umesh.delivery_service.domain.delivery.dto.response.DeliveryStatisticsResponse;
import com.umesh.delivery_service.domain.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/statistics")
    public ResponseEntity<DeliveryStatisticsResponse> getStatistics() {

        return ResponseEntity.ok(
                deliveryService.getStatistics());

    }

    @GetMapping
public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {

    return ResponseEntity.ok(
            deliveryService.getAllDeliveries());

}

}