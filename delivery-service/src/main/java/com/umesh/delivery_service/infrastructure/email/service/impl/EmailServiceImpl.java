package com.umesh.delivery_service.infrastructure.email.service.impl;

import com.umesh.delivery_service.infrastructure.email.dto.EmailRequest;
import com.umesh.delivery_service.infrastructure.email.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(EmailRequest request) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8");

            helper.setFrom(from);

            helper.setTo(request.getTo());

            helper.setSubject(request.getSubject());

            helper.setText(
                    request.getBody(),
                    true);

            mailSender.send(message);

            log.info(
                    "Email sent successfully to {}",
                    request.getTo());

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to send email",
                    ex);

        }

    }

}