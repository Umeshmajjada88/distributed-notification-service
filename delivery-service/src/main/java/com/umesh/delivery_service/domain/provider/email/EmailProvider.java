package com.umesh.delivery_service.domain.provider.email;

import com.umesh.delivery_service.domain.delivery.entity.Delivery;
import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;
import com.umesh.delivery_service.domain.provider.interfaces.NotificationProvider;
import com.umesh.delivery_service.infrastructure.email.dto.EmailRequest;
import com.umesh.delivery_service.infrastructure.email.renderer.EmailTemplateRenderer;
import com.umesh.delivery_service.infrastructure.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailProvider implements NotificationProvider {

    private final EmailService emailService;

    private final EmailTemplateRenderer renderer;

    @Value("${notification.email.default-recipient}")
    private String recipient;

    @Override
    public DeliveryProvider getProvider() {

        return DeliveryProvider.SMTP;

    }

    @Override
    public void send(Delivery delivery) {

        Map<String, Object> variables = new HashMap<>();

        variables.put(
                "subject",
                delivery.getSubject());

        variables.put(
                "message",
                delivery.getMessage());

        variables.put(
                "eventId",
                delivery.getEventId());

        String body = renderer.render(
                "email/notification",
                variables);

        EmailRequest request = EmailRequest.builder()
                .to(recipient)
                .subject(delivery.getSubject())
                .body(body)
                .build();

        emailService.send(request);

    }

}