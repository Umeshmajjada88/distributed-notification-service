package com.umesh.delivery_service.domain.delivery.repository;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByEventId(UUID eventId);

    boolean existsByEventId(UUID eventId);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findTop100ByStatusAndNextRetryAtLessThanEqualOrderByNextRetryAtAsc(
        DeliveryStatus status,
        LocalDateTime time);
}