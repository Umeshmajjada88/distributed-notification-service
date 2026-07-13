package com.umesh.distributed_notification_service.domain.retry.scheduler;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private final NotificationRepository notificationRepository;

    private final NotificationEventMapper eventMapper;

    private final OutboxMapper outboxMapper;

    private final OutboxService outboxService;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processRetries() {

        List<Notification> notifications = notificationRepository
                .findByStatusAndNextRetryAtLessThanEqual(
                        NotificationStatus.RETRYING,
                        LocalDateTime.now());

        if (notifications.isEmpty()) {

            return;

        }

        log.info(
                "Found {} notifications ready for retry.",
                notifications.size());

        for (Notification notification : notifications) {

            NotificationEvent event = eventMapper.toEvent(notification);

            OutboxEvent outboxEvent = outboxMapper.toOutboxEvent(
                    AggregateType.NOTIFICATION,
                    notification.getId().toString(),
                    NotificationEventType.NOTIFICATION_RETRY,
                    event);

            outboxService.save(outboxEvent);

            notification.setStatus(NotificationStatus.PROCESSING);

            notification.setNextRetryAt(null);

            notificationRepository.save(notification);

            log.info(
                    "Retry scheduled for notification {}",
                    notification.getId());

        }

    }

}