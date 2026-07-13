package com.umesh.distributed_notification_service.domain.retry.service.impl;

import com.umesh.distributed_notification_service.config.retry.RetryProperties;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import com.umesh.distributed_notification_service.domain.retry.policy.RetryPolicy;
import com.umesh.distributed_notification_service.domain.retry.service.RetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryServiceImpl implements RetryService {

    private final RetryProperties retryProperties;

    private final NotificationRepository notificationRepository;

    private final NotificationEventMapper eventMapper;

    private final OutboxMapper outboxMapper;

    private final OutboxService outboxService;
    private final RetryPolicy retryPolicy;

    @Override
    @Transactional
    public void retry(Notification notification) {

        notification.setRetryCount(notification.getRetryCount() + 1);

        if (!retryPolicy.shouldRetry(notification.getRetryCount())) {

    notification.setStatus(NotificationStatus.FAILED);

    notification.setNextRetryAt(null);

    notificationRepository.save(notification);

    NotificationEvent event =
            eventMapper.toEvent(notification);

    OutboxEvent outboxEvent =
            outboxMapper.toOutboxEvent(
                    AggregateType.NOTIFICATION,
                    notification.getId().toString(),
                    NotificationEventType.NOTIFICATION_FAILED,
                    event);

    outboxService.save(outboxEvent);

    log.error(
            "Notification {} exceeded maximum retry attempts. Routed to DLQ.",
            notification.getId());

    return;
}

        notification.setStatus(NotificationStatus.RETRYING);

        notification.setNextRetryAt(
                retryPolicy.nextRetryTime(
                        notification.getRetryCount()));

        notificationRepository.save(notification);

        log.info(
                "Notification {} scheduled for retry at {}",
                notification.getId(),
                notification.getNextRetryAt());
    }
}