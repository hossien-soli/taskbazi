package dev.hspl.taskbazi.notification.infrastructure.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.EmailSenderAPI;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultEmailSenderAPI implements EmailSenderAPI {
    private final GlobalEmailSender actualSender;

    @Override
    public void send(EmailAddress targetEmailAddress, UserFriendlyMessage message) {
        actualSender.sendEmailMessage(targetEmailAddress, message);
    }
}
