// package com.umesh.distributed_notification_service.domain.notification.service;

// import com.umesh.distributed_notification_service.common.util.IdGenerator;
// import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
// import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;
// import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
// import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
// import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
// import com.umesh.distributed_notification_service.domain.notification.event.mapper.NotificationRequestedEventMapper;
// import com.umesh.distributed_notification_service.domain.notification.mapper.NotificationMapper;
// import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
// import com.umesh.distributed_notification_service.domain.notification.service.impl.NotificationServiceImpl;
// import com.umesh.distributed_notification_service.domain.outbox.constants.AggregateType;
// import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
// import com.umesh.distributed_notification_service.domain.outbox.entity.OutboxEvent;
// import com.umesh.distributed_notification_service.domain.outbox.mapper.OutboxMapper;
// import com.umesh.distributed_notification_service.domain.outbox.service.OutboxService;
// import com.umesh.distributed_notification_service.infrastructure.metrics.NotificationMetrics;
// import com.umesh.shared.event.NotificationRequestedEvent;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InOrder;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.UUID;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class NotificationServiceTest {

//     @Mock
//     private NotificationRepository notificationRepository;

//     @Mock
//     private NotificationMapper notificationMapper;

//     @Mock
//     private IdGenerator idGenerator;

//     @Mock
//     private NotificationRequestedEventMapper notificationRequestedEventMapper;

//     @Mock
//     private OutboxMapper outboxMapper;

//     @Mock
//     private OutboxService outboxService;

//     @Mock
//     private NotificationMetrics notificationMetrics;

//     @InjectMocks
//     private NotificationServiceImpl notificationService;

//     private CreateNotificationRequest request;
//     private Notification notification;
//     private NotificationResponse response;
//     private NotificationRequestedEvent event;
//     private OutboxEvent outboxEvent;

//     @BeforeEach
//     void setUp() {

//         request = CreateNotificationRequest.builder()
//                 .userId(1L)
//                 .channel(NotificationChannel.EMAIL)
//                 .subject("Welcome")
//                 .message("Hello")
//                 .build();

//         notification = Notification.builder()
//                 .id(1L)
//                 .userId(1L)
//                 .build();

//         response = NotificationResponse.builder()
//                 .id(1L)
//                 .build();

//         event = NotificationRequestedEvent.builder().build();

//         outboxEvent = OutboxEvent.builder().build();
//     }

//     @Test
//     void shouldCreateNotificationSuccessfully() {

//         UUID eventId = UUID.randomUUID();

//         when(notificationMapper.toEntity(request))
//                 .thenReturn(notification);

//         when(idGenerator.generateEventId())
//                 .thenReturn(eventId);

//         when(notificationRepository.save(notification))
//                 .thenReturn(notification);

//         when(notificationRequestedEventMapper.toEvent(notification))
//                 .thenReturn(event);

//         when(outboxMapper.toOutboxEvent(
//                 AggregateType.NOTIFICATION,
//                 "1",
//                 NotificationEventType.NOTIFICATION_REQUESTED,
//                 event))
//                 .thenReturn(outboxEvent);

//         when(notificationMapper.toResponse(notification))
//                 .thenReturn(response);

//         // Act
//         NotificationResponse result =
//                 notificationService.createNotification(request);

//         // Assert
//         assertNotNull(result);
//         assertEquals(response, result);

//         assertEquals(NotificationStatus.PENDING,
//                 notification.getStatus());

//         assertEquals(0,
//                 notification.getRetryCount());

//         assertEquals(eventId,
//                 notification.getEventId());

//         verify(notificationMapper)
//                 .toEntity(request);

//         verify(idGenerator)
//                 .generateEventId();

//         verify(notificationRepository)
//                 .save(notification);

//         verify(notificationRequestedEventMapper)
//                 .toEvent(notification);

//         verify(outboxMapper)
//                 .toOutboxEvent(
//                         AggregateType.NOTIFICATION,
//                         "1",
//                         NotificationEventType.NOTIFICATION_REQUESTED,
//                         event);

//         verify(outboxService)
//                 .save(outboxEvent);

//         verify(notificationMetrics)
//                 .incrementCreated(NotificationChannel.EMAIL);

//         verify(notificationMapper)
//                 .toResponse(notification);

//         InOrder inOrder = inOrder(
//                 notificationRepository,
//                 notificationRequestedEventMapper,
//                 outboxService);

//         inOrder.verify(notificationRepository)
//                 .save(notification);

//         inOrder.verify(notificationRequestedEventMapper)
//                 .toEvent(notification);

//         inOrder.verify(outboxService)
//                 .save(outboxEvent);

//         verifyNoMoreInteractions(
//                 notificationRepository,
//                 notificationRequestedEventMapper,
//                 outboxService,
//                 notificationMetrics);
//     }
// }