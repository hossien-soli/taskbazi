package dev.hspl.taskbazi.user.infrastructure.email;

import dev.hspl.taskbazi.common.infrastructure.email.GenericEmailMessage;
import dev.hspl.taskbazi.common.infrastructure.email.GlobalEmailSender;
import dev.hspl.taskbazi.user.domain.service.ClientRegistrationEmailSender;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.infrastructure.email.messages.ClientRegistrationVerificationEmailMessage;
import dev.hspl.taskbazi.user.infrastructure.email.messages.ClientRegistrationWelcomeEmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserModuleEmailSender implements ClientRegistrationEmailSender {
    private final GlobalEmailSender globalEmailSender;

    @Override
    public void sendVerificationEmail(
            EmailAddress emailAddress,
            PlainVerificationCode verificationCode,
            int sessionLifetimeSeconds,
            LocalDateTime sessionValidUntil,
            ClientFullName clientFullName
    ) {
        GenericEmailMessage emailMessage = new ClientRegistrationVerificationEmailMessage(
                emailAddress,
                clientFullName,
                verificationCode,
                sessionLifetimeSeconds,
                sessionValidUntil
        );

        globalEmailSender.sendEmailMessage(emailAddress,emailMessage);
    }

    @Override
    public void sendWelcomeEmail(
            EmailAddress emailAddress,
            Username clientUsername,
            ClientFullName clientFullName,
            LocalDateTime registrationDateTime
    ) {
        GenericEmailMessage emailMessage = new ClientRegistrationWelcomeEmailMessage(
                emailAddress,
                clientUsername,
                clientFullName,
                registrationDateTime
        );

        globalEmailSender.sendEmailMessage(emailAddress,emailMessage);
    }
}
