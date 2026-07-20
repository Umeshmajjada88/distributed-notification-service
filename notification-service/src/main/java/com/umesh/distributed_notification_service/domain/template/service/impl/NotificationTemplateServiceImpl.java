package com.umesh.distributed_notification_service.domain.template.service.impl;

import com.umesh.distributed_notification_service.common.exception.BadRequestException;
import com.umesh.distributed_notification_service.common.exception.ResourceNotFoundException;
import com.umesh.distributed_notification_service.domain.template.dto.request.CreateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.RenderNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.UpdateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.response.NotificationTemplateResponse;
import com.umesh.distributed_notification_service.domain.template.entity.NotificationTemplate;
import com.umesh.distributed_notification_service.domain.template.enums.NotificationTemplateStatus;
import com.umesh.distributed_notification_service.domain.template.mapper.NotificationTemplateMapper;
import com.umesh.distributed_notification_service.domain.template.renderer.NotificationTemplateRenderer;
import com.umesh.distributed_notification_service.domain.template.repository.NotificationTemplateRepository;
import com.umesh.distributed_notification_service.domain.template.service.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationTemplateServiceImpl
        implements NotificationTemplateService {

    private final NotificationTemplateRepository repository;
    private final NotificationTemplateMapper mapper;
    private final NotificationTemplateRenderer renderer;

    @Override
    public NotificationTemplateResponse createTemplate(
            CreateNotificationTemplateRequest request) {

        if (repository.existsByTemplateCodeAndChannel(
                request.getTemplateCode(),
                request.getChannel())) {

            throw new BadRequestException(
                    "Template already exists for code : "
                            + request.getTemplateCode()
                            + " and channel : "
                            + request.getChannel());
        }

        NotificationTemplate template = mapper.toEntity(request);

        template.setStatus(NotificationTemplateStatus.ACTIVE);
        template.setTemplateVersion(1L);
        template.setIsSystem(Boolean.FALSE);

        NotificationTemplate saved = repository.save(template);

        return mapper.toResponse(saved);
    }

    @Override
    public NotificationTemplateResponse updateTemplate(
            Long id,
            UpdateNotificationTemplateRequest request) {

        NotificationTemplate template = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Template not found : " + id));

        template.setName(request.getName());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setDescription(request.getDescription());

        template.setTemplateVersion(
                template.getTemplateVersion() + 1);

        return mapper.toResponse(
                repository.save(template));
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationTemplateResponse getTemplate(
            Long id) {

        NotificationTemplate template = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Template not found : " + id));

        return mapper.toResponse(template);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationTemplateResponse> getAllTemplates() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteTemplate(Long id) {

        NotificationTemplate template = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Template not found : " + id));

        if (Boolean.TRUE.equals(template.getIsSystem())) {

            throw new BadRequestException(
                    "System templates cannot be deleted.");
        }

        repository.delete(template);
    }

    @Override
@Transactional(readOnly = true)
public String renderTemplate(
        RenderNotificationTemplateRequest request) {

    NotificationTemplate template =
            repository.findByTemplateCodeAndChannel(
                    request.getTemplateCode(),
                    request.getChannel())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Template not found."));

    if (template.getStatus() != NotificationTemplateStatus.ACTIVE) {

        throw new BadRequestException(
                "Template is not active.");
    }

    return renderer.render(
            template.getBody(),
            request.getVariables());
}
}