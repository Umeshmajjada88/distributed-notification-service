package com.umesh.distributed_notification_service.domain.template.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationTemplateRequest {

    @NotBlank(message = "Template name is required")
    private String name;

    private String subject;

    @NotBlank(message = "Template body is required")
    private String body;

    private String description;
}