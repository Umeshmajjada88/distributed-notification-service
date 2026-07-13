package com.umesh.distributed_notification_service.domain.retry.service;

import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import com.umesh.distributed_notification_service.domain.retry.policy.RetryPolicy;
import com.umesh.distributed_notification_service.domain.retry.service.impl.RetryServiceImpl;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetryServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private RetryPolicy retryPolicy;

    @Mock
    private NotificationEventMapper eventMapper;

    @Mock
    private OutboxMapper outboxMapper;

    @Mock
    private OutboxService outboxService;

    @InjectMocks
    private RetryServiceImpl retryService;

    private Notification notification;

    @BeforeEach
    void setUp() {

        notification = new Notification();

        notification.setId(1L);

        notification.setRetryCount(0);

        notification.setStatus(NotificationStatus.PENDING);

    }

    @Test
    void shouldScheduleRetry() {

        when(retryPolicy.shouldRetry(1)).thenReturn(true);

        LocalDateTime retryTime = LocalDateTime.now().plusSeconds(5);

        when(retryPolicy.nextRetryTime(1))
                .thenReturn(retryTime);

        retryService.retry(notification);

        assertEquals(
                NotificationStatus.RETRYING,
                notification.getStatus());

        assertEquals(
                retryTime,
                notification.getNextRetryAt());

        verify(notificationRepository)
                .save(notification);

        verifyNoInteractions(outboxService);

    }

    @Test
    void shouldRouteToDlqWhenRetriesExceeded() {

        notification.setRetryCount(3);

        when(retryPolicy.shouldRetry(4))
                .thenReturn(false);

        NotificationEvent event = new NotificationEvent();

        OutboxEvent outboxEvent = new OutboxEvent();

        when(eventMapper.toEvent(notification))
                .thenReturn(event);

        when(outboxMapper.toOutboxEvent(
                AggregateType.NOTIFICATION,
                "1",
                NotificationEventType.NOTIFICATION_FAILED,
                event))
                .thenReturn(outboxEvent);

        retryService.retry(notification);

        assertEquals(
                NotificationStatus.FAILED,
                notification.getStatus());

        verify(notificationRepository)
                .save(notification);

        verify(outboxService)
                .save(outboxEvent);

    }

}