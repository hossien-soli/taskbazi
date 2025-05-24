package dev.hspl.taskbazi.project.domain.event;

import dev.hspl.taskbazi.common.domain.event.DomainNotificationBroadcastEvent;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.domain.entity.Collaborator;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ProjectCompletedDomainEvent implements DomainNotificationBroadcastEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final ProjectId projectId;

    @Getter
    private final ProjectTitle projectTitle;

    @Getter
    private final UserId projectOwner;

    private final List<Collaborator> projectCollaborators;

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
        if (projectCollaborators != null) {
            return projectCollaborators.stream().filter(Collaborator::isActive)
                    .map(Collaborator::getUserId).toList();
        }

        return Collections.emptyList();
    }

    @Override
    public boolean criticalNotification() {
        return false; // set to true for potential email notification delivery
    }
}
