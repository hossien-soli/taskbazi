package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

// Notification requests for the users
// This abstraction allows for stricter enforcement of domain rules for sending notification
// Because notification delivery is a core domain rule, not merely a side effect.
// This is essentially a marker interface to indicate that the domain requires a notification at this point

// Optional<EmailAddress> notificationUserEmailAddress

public interface DomainNotificationRequestEvent extends DomainEvent {
    UserRole notificationTargetRole();

    UserId notificationUserId();
    EmailAddress notificationUserEmailAddress();

    boolean criticalNotification(); // critical in the perspective of user (may(?should) cause sending email)
}
