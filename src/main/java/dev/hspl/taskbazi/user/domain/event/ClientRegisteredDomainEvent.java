package dev.hspl.taskbazi.user.domain.event;

import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ClientRegisteredDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;
    private final String eventAggregateType;
    private final UUID eventAggregateId;

    private final UserId notificationUserId;
    private final EmailAddress notificationUserEmailAddress;

    @Getter
    private final ClientFullName dataClientFullName;

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
