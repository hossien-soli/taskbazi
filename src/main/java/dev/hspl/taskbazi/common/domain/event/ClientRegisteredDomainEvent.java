package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

// domain notification request event only for clients (UserRole.CLIENT)

@RequiredArgsConstructor
@ToString
public class ClientRegisteredDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    private final UserId clientId;
    private final EmailAddress clientEmailAddress;

    @Getter
    private final String clientFullName;

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
