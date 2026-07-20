package com.umesh.distributed_notification_service.domain.template.renderer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationTemplateRenderer {

    public String render(
            String template,
            Map<String, Object> variables) {

        if (template == null ||
                variables == null ||
                variables.isEmpty()) {

            return template;
        }

        String renderedTemplate = template;

        for (Map.Entry<String, Object> entry : variables.entrySet()) {

            String placeholder = "{{" + entry.getKey() + "}}";

            renderedTemplate = renderedTemplate.replace(
                    placeholder,
                    entry.getValue() == null
                            ? ""
                            : String.valueOf(entry.getValue()));
        }

        return renderedTemplate;
    }
}