package com.umesh.distributed_notification_service.domain.delivery.service;

import com.umesh.distributed_notification_service.domain.delivery.factory.DeliveryStrategyFactory;
import com.umesh.distributed_notification_service.domain.delivery.service.impl.DeliveryServiceImpl;
import com.umesh.distributed_notification_service.domain.notification.entity.Notification;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationStatus;
import com.umesh.distributed_notification_service.domain.notification.event.dto.NotificationEvent;
import com.umesh.distributed_notification_service.domain.notification.repository.NotificationRepository;
import com.umesh.distributed_notification_service.domain.retry.service.RetryService;
import com.umesh.distributed_notification_service.infrastructure.metrics.NotificationMetrics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryStrategyFactory strategyFactory;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationDeliveryService notificationDeliveryService;

    @Mock
    private RetryService retryService;

    @Mock
    private com.umesh.distributed_notification_service.domain.delivery.strategy.NotificationDeliveryStrategy strategy;

    @Mock
        private NotificationMetrics notificationMetrics;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void shouldDeliverSuccessfully() {

        Notification notification = Notification.builder()
                .id(1L)
                .channel(NotificationChannel.EMAIL)
                .retryCount(0)
                .build();

        NotificationEvent event = NotificationEvent.builder()
                .notificationId(1L)
                .channel(NotificationChannel.EMAIL)
                .build();

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(notification));

        when(strategyFactory.getStrategy(NotificationChannel.EMAIL))
                .thenReturn(strategy);

        deliveryService.deliver(event);

        verify(strategy).deliver(event);

        verify(notificationRepository)
                .save(notification);
    }

    @Test
    void shouldRetryWhenDeliveryFails() {

        Notification notification = Notification.builder()
                .id(1L)
                .channel(NotificationChannel.EMAIL)
                .retryCount(0)
                .build();

        NotificationEvent event = NotificationEvent.builder()
                .notificationId(1L)
                .channel(NotificationChannel.EMAIL)
                .build();

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(notification));

        when(strategyFactory.getStrategy(NotificationChannel.EMAIL))
                .thenReturn(strategy);

        doThrow(new RuntimeException("SMTP Down"))
                .when(strategy)
                .deliver(event);

        deliveryService.deliver(event);

        verify(retryService)
                .retry(notification);
    }
}