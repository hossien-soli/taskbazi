package dev.hspl.taskbazi.user.infrastructure.email.messages;

import dev.hspl.taskbazi.common.infrastructure.email.GenericEmailMessage;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;

public record ClientRegistrationWelcomeEmailMessage(
        EmailAddress clientEmailAddress,
        Username clientUsername,
        ClientFullName clientFullName,
        LocalDateTime registrationDateTime
) implements GenericEmailMessage {
    @Override
    @NonNull
    public String prepareSubject(MessageSource messageSource) {
        return "";
    }

    @Override
    @NonNull
    public String prepareSimpleBody(MessageSource messageSource) {
        return "";
    }

    @Override
    @Nullable
    public String prepareHTMLBody(MessageSource messageSource, TemplateEngine templateEngine) {
        return "";
    }
}
