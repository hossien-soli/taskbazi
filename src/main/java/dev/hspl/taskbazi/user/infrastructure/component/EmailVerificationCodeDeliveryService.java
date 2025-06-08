package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.EmailSenderAPI;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.user.domain.service.VerificationCodeDeliveryService;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.UserFullName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmailVerificationCodeDeliveryService implements VerificationCodeDeliveryService {
    private final EmailSenderAPI emailSenderAPI;
    private final MessageSource messageSource; // Necessary for constructing the email message content
    private final TemplateEngine htmlTemplateEngine; // Necessary for constructing the email message content

    @Override
    public void deliver(
            String deliveryTarget,
            PlainVerificationCode verificationCode,
            int sessionLifetimeSeconds,
            LocalDateTime sessionValidUntil,
            UserFullName clientFullName
    ) {
        // TODO: create a pretty body for email verification message
        String subject = "";
        String plainTextBody = "";
        String htmlBody = "";
        boolean isImportant = true;

        UserFriendlyMessage emailMessage = new UserFriendlyMessage(subject, plainTextBody, htmlBody, isImportant);
        emailSenderAPI.send(new EmailAddress(deliveryTarget), emailMessage);
    }
}
