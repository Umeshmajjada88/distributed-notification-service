package com.umesh.distributed_notification_service.domain.notification.dto.request;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Notification channel is required")
    private NotificationChannel channel;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private String subject;

    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledAt;

}