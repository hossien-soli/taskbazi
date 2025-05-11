package dev.hspl.taskbazi.common.infrastructure.message.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;

// implementations: OutboxingGlobalEmailSender, DirectGlobalEmailSender, AsyncGlobalEmailSender
// OutboxingGlobalEmailSender -> Always call this within a transaction context

public interface GlobalEmailSender {
    void sendEmailMessage(EmailAddress targetEmailAddress, UserFriendlyMessage message);
}
