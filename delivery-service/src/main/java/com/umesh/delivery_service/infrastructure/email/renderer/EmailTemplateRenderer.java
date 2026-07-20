package com.umesh.delivery_service.infrastructure.email.renderer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailTemplateRenderer {

    private final TemplateEngine templateEngine;

    public String render(
            String template,
            Map<String, Object> variables) {

        Context context = new Context();

        context.setVariables(variables);

        return templateEngine.process(
                template,
                context);

    }

}