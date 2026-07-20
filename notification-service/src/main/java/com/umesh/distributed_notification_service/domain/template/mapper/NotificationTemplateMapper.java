package com.umesh.distributed_notification_service.domain.template.mapper;

import com.umesh.distributed_notification_service.domain.template.dto.request.CreateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.response.NotificationTemplateResponse;
import com.umesh.distributed_notification_service.domain.template.entity.NotificationTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateMapper {

    public NotificationTemplate toEntity(CreateNotificationTemplateRequest request) {

        if (request == null) {
            return null;
        }

        return NotificationTemplate.builder()
                .templateCode(request.getTemplateCode())
                .name(request.getName())
                .channel(request.getChannel())
                .subject(request.getSubject())
                .body(request.getBody())
                .description(request.getDescription())
                .build();
    }

    public NotificationTemplateResponse toResponse(NotificationTemplate template) {

        if (template == null) {
            return null;
        }

        return NotificationTemplateResponse.builder()
                .id(template.getId())
                .templateCode(template.getTemplateCode())
                .name(template.getName())
                .channel(template.getChannel())
                .subject(template.getSubject())
                .body(template.getBody())
                .description(template.getDescription())
                .status(template.getStatus())
                .templateVersion(template.getTemplateVersion())
                .isSystem(template.getIsSystem())
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}