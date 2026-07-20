package com.umesh.distributed_notification_service.domain.template.controller;

import com.umesh.distributed_notification_service.common.dto.ApiResponse;
import com.umesh.distributed_notification_service.common.util.ApiResponseBuilder;
import com.umesh.distributed_notification_service.domain.template.dto.request.CreateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.RenderNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.request.UpdateNotificationTemplateRequest;
import com.umesh.distributed_notification_service.domain.template.dto.response.NotificationTemplateResponse;
import com.umesh.distributed_notification_service.domain.template.service.NotificationTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
@Tag(name = "Notification Template API")
public class NotificationTemplateController {

    private final NotificationTemplateService service;

    @PostMapping
    @Operation(summary = "Create template")
    public ResponseEntity<ApiResponse<NotificationTemplateResponse>> createTemplate(
            @Valid @RequestBody CreateNotificationTemplateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(
                        "Template created successfully.",
                        service.createTemplate(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationTemplateResponse>> updateTemplate(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNotificationTemplateRequest request) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Template updated successfully.",
                        service.updateTemplate(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationTemplateResponse>> getTemplate(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Template retrieved successfully.",
                        service.getTemplate(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationTemplateResponse>>> getAllTemplates() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Templates retrieved successfully.",
                        service.getAllTemplates()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTemplate(@PathVariable Long id) {

        service.deleteTemplate(id);

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Template deleted successfully.",
                        null));
    }

    @PostMapping("/render")
    public ResponseEntity<ApiResponse<String>> renderTemplate(
            @Valid @RequestBody RenderNotificationTemplateRequest request) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Template rendered successfully.",
                        service.renderTemplate(request)));
    }
}