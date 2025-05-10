package dev.hspl.taskbazi.common.infrastructure.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;

// implementations: OutboxingGlobalEmailSender, DirectGlobalEmailSender

public interface GlobalEmailSender {
    void sendEmailMessage(EmailAddress targetEmailAddress, GenericEmailMessage message);
}
