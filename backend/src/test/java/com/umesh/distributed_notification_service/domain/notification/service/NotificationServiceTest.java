package com.umesh.distributed_notification_service.domain.notification.service;

import com.umesh.distributed_notification_service.common.util.IdGenerator;
import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationEventMapper;
import com.umesh.distributed_notification_service.domain.notification.mapper.NotificationMapper;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import com.umesh.distributed_notification_service.domain.notification.service.impl.NotificationServiceImpl;
import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
import com.umesh.distributed_notification_service.infrastructure.metrics.NotificationMetrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private NotificationEventMapper notificationEventMapper;

    @Mock
    private OutboxMapper outboxMapper;

    @Mock
    private OutboxService outboxService;

    @Mock
    private NotificationMetrics notificationMetrics;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private CreateNotificationRequest request;
    private Notification notification;
    private NotificationResponse response;
    private NotificationEvent event;
    private OutboxEvent outboxEvent;

    @BeforeEach
    void setUp() {

        request = CreateNotificationRequest.builder()
                .userId(1L)
                .channel(NotificationChannel.EMAIL)
                .subject("Welcome")
                .message("Hello")
                .build();

        notification = Notification.builder()
                .id(1L)
                .userId(1L)
                .build();

        response = NotificationResponse.builder()
                .id(1L)
                .build();

        event = NotificationEvent.builder().build();

        outboxEvent = OutboxEvent.builder().build();
    }

    @Test
    void shouldCreateNotificationSuccessfully() {

        UUID eventId = UUID.randomUUID();

        when(notificationMapper.toEntity(request))
                .thenReturn(notification);

        when(idGenerator.generateEventId())
                .thenReturn(eventId);

        when(notificationRepository.save(notification))
                .thenReturn(notification);

        when(notificationEventMapper.toEvent(notification))
                .thenReturn(event);

        when(outboxMapper.toOutboxEvent(
                AggregateType.NOTIFICATION,
                "1",
                NotificationEventType.NOTIFICATION_CREATED,
                event))
                .thenReturn(outboxEvent);

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        NotificationResponse result = notificationService.createNotification(request);

        assertNotNull(result);

        assertEquals(NotificationStatus.PENDING,
                notification.getStatus());

        assertEquals(0,
                notification.getRetryCount());

        assertEquals(eventId,
                notification.getEventId());

        verify(notificationRepository).save(notification);
        verify(outboxService).save(outboxEvent);
        verify(notificationMapper).toResponse(notification);
    }
}