package com.umesh.distributed_notification_service.domain.template.repository;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.template.entity.NotificationTemplate;
import com.umesh.distributed_notification_service.domain.template.enums.NotificationTemplateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate> findByTemplateCodeAndChannel(
            String templateCode,
            NotificationChannel channel);

    boolean existsByTemplateCodeAndChannel(
            String templateCode,
            NotificationChannel channel);

    List<NotificationTemplate> findAllByStatus(
            NotificationTemplateStatus status);

    List<NotificationTemplate> findAllByChannel(
            NotificationChannel channel);

    boolean existsByTemplateCode(
            String templateCode);
}