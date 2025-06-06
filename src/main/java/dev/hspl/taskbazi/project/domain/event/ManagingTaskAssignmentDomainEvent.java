package dev.hspl.taskbazi.project.domain.event;

import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import dev.hspl.taskbazi.project.domain.value.TaskPriority;
import dev.hspl.taskbazi.project.domain.value.TaskTitle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// new task assigned to a collaborator by owner or managingAssignment collaborator to another collaborator(not a self assignment!!)

@RequiredArgsConstructor
public class ManagingTaskAssignmentDomainEvent implements DomainNotificationRequestEvent {
    private final LocalDateTime currentDateTime;

    private final UserId notificationUserId;
    private final EmailAddress notificationUserEmailAddress;

    @Getter
    private final ProjectId relatedProjectId;

    @Getter
    private final ProjectTitle relatedProjectTitle;

    @Getter
    private final TaskTitle taskTitle;

    @Getter
    private final TaskPriority taskPriority;

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
