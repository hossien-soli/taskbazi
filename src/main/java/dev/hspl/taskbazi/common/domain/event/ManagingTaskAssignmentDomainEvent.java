package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// new task assigned to a collaborator by owner or managingAssignment collaborator to another collaborator(not a self assignment!!)

@RequiredArgsConstructor
public class ManagingTaskAssignmentDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    private final UserId notificationUserId;
    private final EmailAddress notificationUserEmailAddress;

    @Getter
    private final UUID relatedProjectId;

    @Getter
    private final String relatedProjectTitle;

    @Getter
    private final String taskTitle;

    @Getter
    private final String taskPriority; // at the end(if json serialization needed) we should treat everything as string

    @Override
    public LocalDateTime eventOccurredAt() {
        return currentDateTime;
    }

    @Override
    public UserRole notificationTargetRole() {
        return UserRole.CLIENT;
    }

    @Override
    public UserId notificationUserId() {
        return notificationUserId;
    }

    @Override
    public EmailAddress notificationUserEmailAddress() {
        return notificationUserEmailAddress;
    }

    @Override
    public boolean criticalNotification() {
        return true;
    }
}
