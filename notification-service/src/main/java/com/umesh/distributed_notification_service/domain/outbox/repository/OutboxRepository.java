package com.umesh.distributed_notification_service.domain.outbox.repository;

import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(
            OutboxStatus status);

}