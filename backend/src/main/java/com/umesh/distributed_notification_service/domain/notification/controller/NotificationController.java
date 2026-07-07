package com.umesh.distributed_notification_service.domain.notification.controller;

import com.umesh.distributed_notification_service.common.dto.ApiResponse;
import com.umesh.distributed_notification_service.domain.notification.dto.request.CreateNotificationRequest;
import com.umesh.distributed_notification_service.domain.notification.dto.response.NotificationResponse;
import com.umesh.distributed_notification_service.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umesh.distributed_notification_service.common.util.ApiResponseBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification API", description = "APIs for managing notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create a new notification")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {

        NotificationResponse response = notificationService.createNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(
                        "Notification created successfully.",
                        response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by id")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationById(
            @PathVariable Long id) {

        NotificationResponse response = notificationService.getNotificationById(id);

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Notification retrieved successfully.",
                        response));
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get notification by event id")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationByEventId(
            @PathVariable UUID eventId) {

        NotificationResponse response = notificationService.getNotificationByEventId(eventId);

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Notifications retrieved successfully.",
                        response));
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {

        List<NotificationResponse> response = notificationService.getAllNotifications();

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Notifications retrieved successfully.",
                        response));
    }
}