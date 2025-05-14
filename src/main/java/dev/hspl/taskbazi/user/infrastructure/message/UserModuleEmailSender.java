package dev.hspl.taskbazi.user.infrastructure.message;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.email.GlobalEmailSender;
import dev.hspl.taskbazi.user.domain.service.ClientRegistrationEmailSender;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserModuleEmailSender implements ClientRegistrationEmailSender {
    private final GlobalEmailSender globalEmailSender;
    private final MessageSource messageSource; // Necessary for constructing the email message content
    private final TemplateEngine htmlTemplateEngine; // Necessary for constructing the email message content

    @Override
    public void sendVerificationEmail(
            EmailAddress emailAddress,
            PlainVerificationCode verificationCode,
            int sessionLifetimeSeconds,
            LocalDateTime sessionValidUntil,
            ClientFullName clientFullName
    ) {
        // TODO: create a pretty body for email verification message
        String subject = "";
        String plainTextBody = "";
        String htmlBody = "";
        boolean isImportant = true;

        UserFriendlyMessage emailMessage = new UserFriendlyMessage(subject,plainTextBody,htmlBody,isImportant);
        globalEmailSender.sendEmailMessage(emailAddress,emailMessage);
    }
}
