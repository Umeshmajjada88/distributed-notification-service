package com.umesh.distributed_notification_service.domain.notification.service;

import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    NotificationResponse createNotification(
            CreateNotificationRequest request);

    NotificationResponse getNotificationById(
            Long id);

    NotificationResponse getNotificationByEventId(
            UUID eventId);

    List<NotificationResponse> getAllNotifications();

}