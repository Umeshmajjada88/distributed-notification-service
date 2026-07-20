package com.umesh.distributed_notification_service.domain.template.service;

import com.umesh.distributed_notification_service.domain.template.dto.request.CreateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.RenderNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.UpdateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.response.NotificationTemplateResponse;

import java.util.List;

public interface NotificationTemplateService {

    NotificationTemplateResponse createTemplate(
            CreateNotificationTemplateRequest request);

    NotificationTemplateResponse updateTemplate(
            Long id,
            UpdateNotificationTemplateRequest request);

    NotificationTemplateResponse getTemplate(
            Long id);

    List<NotificationTemplateResponse> getAllTemplates();

    void deleteTemplate(
            Long id);

    String renderTemplate(
        RenderNotificationTemplateRequest request
);
}