package com.umesh.distributed_notification_service.domain.notification.repository;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByEventId(UUID eventId);

    boolean existsByEventId(UUID eventId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByStatusAndScheduledAtBefore(
            NotificationStatus status,
            LocalDateTime scheduledAt);

}