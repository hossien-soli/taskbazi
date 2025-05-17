package dev.hspl.taskbazi.common.infrastructure.message.notification;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

public record NotificationRecipient(
        UserRole userRole,
        UserId userId,
        EmailAddress emailAddress
) {
    public NotificationRecipient {
        boolean validate = userRole != null && userId != null && emailAddress != null;
        if (!validate) {
            throw new IllegalStateException("all fields of notification-recipient record are required!!!");
        }
    }
}

// make recipient fields optional based on delivery methods