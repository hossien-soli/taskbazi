package dev.hspl.taskbazi.user.domain.event;

import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.value.UserFullName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// domain notification request event only for clients (UserRole.CLIENT)

@RequiredArgsConstructor
public class ClientRegisteredDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    private final UserId clientId;
    private final EmailAddress clientEmailAddress;

    @Getter
    private final UserFullName clientFullName;

    @Getter
    private final Username clientUsername;

    @Override
    public LocalDateTime eventOccurredAt() {
        return this.currentDateTime;
    }

    @Override
    public UserRole notificationTargetRole() {
        return UserRole.CLIENT;
    }

    @Override
    public UserId notificationUserId() {
        return this.clientId;
    }

    @Override
    public EmailAddress notificationUserEmailAddress() {
        return this.clientEmailAddress;
    }

    @Override
    public boolean criticalNotification() {
        return true;
    }
}
