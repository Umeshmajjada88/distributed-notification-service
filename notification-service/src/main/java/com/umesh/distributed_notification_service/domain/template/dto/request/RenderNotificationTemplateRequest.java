package com.umesh.distributed_notification_service.domain.template.dto.request;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenderNotificationTemplateRequest {

    @NotBlank(message = "Template code is required")
    private String templateCode;

    @NotNull(message = "Channel is required")
    private NotificationChannel channel;

    @Builder.Default
    private Map<String, Object> variables = Map.of();
}