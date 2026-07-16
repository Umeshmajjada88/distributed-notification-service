package com.umesh.distributed_notification_service.domain.notification.service.impl;

import com.umesh.distributed_notification_service.common.exception.ResourceNotFoundException;
import com.umesh.distributed_notification_service.common.util.IdGenerator;
import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.notification.event.publisher.NotificationEventPublisher;
import com.umesh.distributed_notification_service.domain.notification.mapper.NotificationMapper;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import com.umesh.distributed_notification_service.domain.notification.service.NotificationService;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import com.umesh.distributed_notification_service.infrastructure.metrics.NotificationMetrics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final IdGenerator idGenerator;
    // private final NotificationEventPublisher notificationEventPublisher;
    private final NotificationEventMapper notificationEventMapper;

    private final OutboxMapper outboxMapper;

    private final OutboxService outboxService;

    private final NotificationMetrics notificationMetrics;

    @Override
public NotificationResponse createNotification(
        CreateNotificationRequest request) {

    Notification notification = initializeNotification(request);

    Notification savedNotification =
            notificationRepository.save(notification);

    NotificationEvent event =
            notificationEventMapper.toEvent(savedNotification);

    OutboxEvent outboxEvent =
            outboxMapper.toOutboxEvent(
                    AggregateType.NOTIFICATION,
                    savedNotification.getId().toString(),
                    NotificationEventType.NOTIFICATION_CREATED,
                    event);

    outboxService.save(outboxEvent);

    notificationMetrics.incrementCreated();

    return notificationMapper.toResponse(savedNotification);
}

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with id : " + id));

        return notificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationByEventId(UUID eventId) {

        Notification notification = notificationRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with event id : " + eventId));

        return notificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    private Notification initializeNotification(CreateNotificationRequest request) {

        Notification notification = notificationMapper.toEntity(request);

        notification.setEventId(idGenerator.generateEventId());

        notification.setStatus(determineInitialStatus(request));

        notification.setRetryCount(0);

        return notification;
    }

    private NotificationStatus determineInitialStatus(
            CreateNotificationRequest request) {

        if (request.getScheduledAt() != null &&
                request.getScheduledAt().isAfter(LocalDateTime.now())) {

            return NotificationStatus.SCHEDULED;
        }

        return NotificationStatus.PENDING;
    }

    // private Notification saveNotification(Notification notification) {

    //     return notificationRepository.save(notification);
    // }
}