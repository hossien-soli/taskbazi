package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

import java.util.Collection;

// Notification requests for the users
// Broadcast notification to multiple users
// This abstraction allows for stricter enforcement of domain rules for sending notification
// Because notification delivery is a core domain rule, not merely a side effect.
// This is essentially a marker interface to indicate that the domain requires a notification at this point

// Optional<Collection<EmailAddress>> notificationUserEmailAddresses

// DomainNotificationBroadcastRequestEvent
public interface DomainNotificationBroadcastEvent extends DomainEvent {
    UserRole notificationTargetRole();

    // don't return null!!!
    Collection<UserId> notificationUserIds();

    boolean criticalNotification(); // critical in the perspective of user (may(?should) cause sending email)
}
