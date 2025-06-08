package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProjectCompletedDomainEvent implements DomainNotificationBroadcastEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final UUID projectId;

    @Getter
    private final String projectTitle;

    @Getter
    private final UserId projectOwner;

    private final List<UserId> notificationUserIds; // make sure only include active collaborators of project

    @Override
    public LocalDateTime eventOccurredAt() {
        return currentDateTime;
    }

    @Override
    public UserRole notificationTargetRole() {
        return UserRole.CLIENT;
    }

    @Override
    public Collection<UserId> notificationUserIds() {
        return notificationUserIds;
    }

    @Override
    public boolean criticalNotification() {
        return false; // set to true for potential email notification delivery
    }
}
