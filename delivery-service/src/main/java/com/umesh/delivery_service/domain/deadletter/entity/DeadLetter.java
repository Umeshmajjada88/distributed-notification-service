package com.umesh.delivery_service.domain.deadletter.entity;

import com.umesh.delivery_service.common.entity.BaseEntity;
import com.umesh.delivery_service.domain.deadletter.enums.DeadLetterStatus;
import com.umesh.shared.types.NotificationChannel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dead_letter")
public class DeadLetter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long deliveryId;

    @Column(nullable = false)
    private Long notificationId;

    @Column(nullable = false)
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannel channel;

    @Column(nullable = false, length = 50)
    private String provider;

    @Column(nullable = false)
    private Integer attemptCount;

    @Column(nullable = false, length = 500)
    private String failureReason;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeadLetterStatus status;

}