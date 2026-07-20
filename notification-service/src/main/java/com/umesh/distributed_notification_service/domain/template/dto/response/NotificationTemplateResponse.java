package com.umesh.distributed_notification_service.domain.template.dto.response;

import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.template.enums.NotificationTemplateStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplateResponse {

    private Long id;

    private String templateCode;

    private String name;

    private NotificationChannel channel;

    private String subject;

    private String body;

    private String description;

    private NotificationTemplateStatus status;

    private Long templateVersion;

    private Boolean isSystem;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}