package com.umesh.shared.event;

import com.umesh.shared.types.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestedEvent implements Serializable {

    private UUID eventId;

    private Long notificationId;

    private Long userId;

    private NotificationChannel channel;

    private String subject;

    private String message;
}