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
import java.util.UUID;

// domain notification request event only for clients (UserRole.CLIENT)

@RequiredArgsConstructor
public class ClientRegisteredDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;
    private final String eventAggregateType;
    private final UUID eventAggregateId;

    private final UserId notificationUserId;
    private final EmailAddress notificationUserEmailAddress;

    @Getter
    private final UserFullName dataClientFullName;

    @Getter
    private final Username dataClientUsername;

    @Override
    public LocalDateTime eventOccurredAt() {
        return this.currentDateTime;
    }

    @Override
    public String eventAggregateType() {
        return this.eventAggregateType;
    }

    @Override
    public UUID eventAggregateId() {
        return this.eventAggregateId;
    }

    @Override
    public UserRole notificationUserRole() {
        return UserRole.CLIENT;
    }

    @Override
    public UserId notificationUserId() {
        return this.notificationUserId;
    }

    @Override
    public EmailAddress notificationUserEmailAddress() {
        return this.notificationUserEmailAddress;
    }
}
