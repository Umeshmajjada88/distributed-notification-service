package com.umesh.distributed_notification_service.domain.provider.email;

import com.umesh.distributed_notification_service.domain.provider.email.dto.EmailRequest;
import com.umesh.distributed_notification_service.domain.provider.email.exception.EmailDeliveryException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringMailEmailProvider implements EmailProvider {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Override
    public void send(EmailRequest request) {

        try {

            Context context = new Context();

            context.setVariable("subject", request.getSubject());
            context.setVariable("message", request.getBody());

            String html = templateEngine.process(
                    "email/notification",
                    context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8");

            helper.setTo(request.getTo());

            helper.setSubject(request.getSubject());

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            log.info(
                    "HTML email sent successfully to {}",
                    request.getTo());

        } catch (Exception ex) {

            throw new EmailDeliveryException(
                    "Failed to send HTML email.",
                    ex);

        }

    }
}