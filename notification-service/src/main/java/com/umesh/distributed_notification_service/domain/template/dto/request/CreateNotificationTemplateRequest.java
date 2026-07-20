package com.umesh.distributed_notification_service.domain.template.dto.request;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationTemplateRequest {

    @NotBlank(message = "Template code is required")
    private String templateCode;

    @NotBlank(message = "Template name is required")
    private String name;

    @NotNull(message = "Notification channel is required")
    private NotificationChannel channel;

    private String subject;

    @NotBlank(message = "Template body is required")
    private String body;

    private String description;
}