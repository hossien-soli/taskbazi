package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// this is a public event in whole system
// both the user module and the admin module may use it
// The admin module needs it to detect malicious behavior
// The user module is only using it to send a notification to the related user

@RequiredArgsConstructor
public class RefreshTokenReuseDetectedAlertEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final UUID dataRefreshTokenId;

    @Getter
    private final UserRole dataRelatedUserRole;

    @Getter
    private final UserId dataRelatedUserId;

    @Getter
    private final RequestClientIdentifier dataRequestClientIdentifier; // not-null

    @Getter
    private final RequestIdentificationDetails dataRequestIdentificationDetails; // nullable

    private final EmailAddress notificationUserEmailAddress;

    @Override
    public LocalDateTime eventOccurredAt() {
        return this.currentDateTime;
    }

    @Override
    public UserRole notificationTargetRole() {
        return this.dataRelatedUserRole;
    }

    @Override
    public UserId notificationUserId() {
        return this.dataRelatedUserId;
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
