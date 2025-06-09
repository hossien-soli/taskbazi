package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// new task assigned by owner or a managingAssignment collaborator to another collaborator(not a self assignment!!)

@RequiredArgsConstructor
@ToString
public class ManagingTaskAssignmentDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final UserId targetUserId; // target collaborator
    private final EmailAddress targetUserEmailAddress; // target collaborator

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
        return targetUserId;
    }

    @Override
    public EmailAddress notificationUserEmailAddress() {
        return targetUserEmailAddress;
    }

    @Override
    public boolean criticalNotification() {
        return true;
    }
}
