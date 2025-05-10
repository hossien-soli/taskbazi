package dev.hspl.taskbazi.common.infrastructure.email;

import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thymeleaf.TemplateEngine;

public interface GenericEmailMessage {
    @NonNull
    String prepareSubject(MessageSource messageSource); // max:100

    @NonNull
    String prepareSimpleBody(MessageSource messageSource);

    @Nullable
    String prepareHTMLBody(MessageSource messageSource, TemplateEngine templateEngine); // null = no html body!!!
}
