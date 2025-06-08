package dev.hspl.taskbazi.notification.infrastructure.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;

// implementations: OutboxingGlobalEmailSender, DirectGlobalEmailSender, AsyncGlobalEmailSender
// Direct?? -> It means the sending logic executes/calls directly within the current active transaction, rather than asynchronously (async) or through any other execution method
// Outboxing?? -> always call it also inside the active transaction

public interface GlobalEmailSender {
    void sendEmailMessage(EmailAddress targetEmailAddress, UserFriendlyMessage message);
}
