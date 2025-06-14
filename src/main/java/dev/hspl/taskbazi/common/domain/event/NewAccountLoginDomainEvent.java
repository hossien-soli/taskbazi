package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.RequestIdentificationDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@ToString
public class NewAccountLoginDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    private final UserRole notificationUserRole;
    private final UserId notificationUserId;
    private final EmailAddress notificationUserEmailAddress;

    @Getter
    private final RequestClientIdentifier dataRequestClientIdentifier; // required

    @Getter
    private final RequestIdentificationDetails dataRequestIdentificationDetails; // nullable

    @Getter
    private final UUID dataNewLoginSessionId; // required

    @Override
    public LocalDateTime eventOccurredAt() {
        return this.currentDateTime;
    }

    @Override
    public UserRole notificationTargetRole() {
        return this.notificationUserRole;
    }

    @Override
    public UserId notificationUserId() {
        return this.notificationUserId;
    }

    @Override
    public EmailAddress notificationUserEmailAddress() {
        return this.notificationUserEmailAddress;
    }

    @Override
    public boolean criticalNotification() {
        return true;
    }
}
