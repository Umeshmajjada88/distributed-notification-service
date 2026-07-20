package com.umesh.distributed_notification_service.domain.template.entity;

import com.umesh.distributed_notification_service.common.entity.BaseEntity;
import com.umesh.distributed_notification_service.domain.notification.enums.NotificationChannel;
import com.umesh.distributed_notification_service.domain.template.enums.NotificationTemplateStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notification_template", uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_template_code_channel", columnNames = { "template_code", "channel" })
}, indexes = {
        @Index(name = "idx_notification_template_status", columnList = "status"),
        @Index(name = "idx_notification_template_channel", columnList = "channel")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_code", nullable = false, length = 100)
    private String templateCode;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationChannel channel;

    @Column(length = 255)
    private String subject;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationTemplateStatus status;

    @Column(name = "template_version", nullable = false)
    private Long templateVersion;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;
}