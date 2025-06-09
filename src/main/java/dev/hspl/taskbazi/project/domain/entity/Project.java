package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationBroadcastEvent;
import dev.hspl.taskbazi.common.domain.event.ProjectEditedDomainEvent;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.event.ProjectStartedDomainEvent;
import dev.hspl.taskbazi.project.domain.exception.ProjectIsNotEditableException;
import dev.hspl.taskbazi.project.domain.exception.ProjectIsNotStartableException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectStatus;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public class Project extends DomainAggregateRoot {
    private final ProjectId id;
    private final UserId owner;

    private ProjectTitle title;
    private Description description; // optional-nullable - check for cross-site scripting in the infrastructure!!!

    private ProjectStatus status;

    private final LocalDateTime registeredAt;
    private LocalDateTime startedAt; // nullable
    private LocalDateTime closedAt; // nullable | Archived or Completed or Cancelled or Blocked
    private LocalDateTime editedAt; // nullable

    private final List<Collaborator> collaborators; // nullable or empty list (final for only reference)

    private final Integer version; // just a data transfer property

    private Project(
            ProjectId id,
            UserId owner,
            ProjectTitle title,
            Description description,
            ProjectStatus status,
            LocalDateTime registeredAt,
            LocalDateTime startedAt,
            LocalDateTime closedAt,
            LocalDateTime editedAt,
            List<Collaborator> collaborators,
            Integer version
    ) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.status = status;
        this.registeredAt = registeredAt;
        this.startedAt = startedAt;
        this.closedAt = closedAt;
        this.editedAt = editedAt;
        this.collaborators = collaborators;
        this.version = version;
    }

    public static Project registerNewProject(
            LocalDateTime currentDateTime,
            ProjectId newProjectId,
            UniversalUser registrarUser,
            ProjectTitle title,
            Description description // nullable
    ) {
        // TODO: add validation(null-check) for required fields
        boolean checkAccount = registrarUser.userRole().equals(UserRole.CLIENT) && registrarUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        return new Project(newProjectId,registrarUser.universalUserId(),title,description,ProjectStatus.REGISTERED,currentDateTime,
                null,null,null,null,null);
    }

    public static Project existingProject(
            ProjectId id,
            UserId owner,
            ProjectTitle title,
            Description description,
            ProjectStatus status,
            LocalDateTime registeredAt,
            LocalDateTime startedAt,
            LocalDateTime closedAt,
            LocalDateTime editedAt,
            List<Collaborator> collaborators,
            Integer version
    ) {
        // TODO: add validation(null-check) for required fields
        return new Project(id,owner,title,description,status,registeredAt,startedAt,closedAt,editedAt,collaborators,version);
    }

    public boolean isClosed() {
        return this.status.equals(ProjectStatus.ARCHIVED) || this.status.equals(ProjectStatus.COMPLETED)
                || this.status.equals(ProjectStatus.CANCELLED) || this.status.equals(ProjectStatus.BLOCKED);
    }

    public void startProject(LocalDateTime currentDateTime) {
        boolean isStartable = this.status.equals(ProjectStatus.REGISTERED) || this.status.equals(ProjectStatus.ARCHIVED);
        if (!isStartable) {
            throw new ProjectIsNotStartableException();
        }

        this.status = ProjectStatus.IN_PROGRESS;
        this.startedAt = currentDateTime;

        List<UserId> notificationUserIds = Collections.emptyList();
        if (this.collaborators != null) {
            notificationUserIds = this.collaborators.stream().filter(Collaborator::isActive)
                    .map(Collaborator::getUserId).toList();
        }

        DomainNotificationBroadcastEvent domainEvent = new ProjectStartedDomainEvent(
                currentDateTime,
                this.id.value(),
                this.title.value(),
                this.owner,
                notificationUserIds
        );

        registerDomainEvent(domainEvent);
    }

    public void archiveProject(LocalDateTime currentDateTime) {

    }

    public void completeProject(LocalDateTime currentDateTime) {

    }

    public void cancelProject(LocalDateTime currentDateTime) {

    }

    public void edit(
            LocalDateTime currentDateTime,
            ProjectTitle newTitle,
            Description newDescription  // nullable-optional
    ) {
        // TODO: add null-check for value objects
        boolean editable = this.status.equals(ProjectStatus.REGISTERED) || this.status.equals(ProjectStatus.IN_PROGRESS)
                || this.status.equals(ProjectStatus.ARCHIVED);
        if (!editable) {
            throw new ProjectIsNotEditableException();
        }

        registerDomainEvent(new ProjectEditedDomainEvent(
                currentDateTime,this.id.value(),
                this.title.value(),newTitle.value(),
                this.description != null ? this.description.value() : null,
                newDescription != null ? newDescription.value() : null,
                this.version
        ));

        this.title = newTitle;
        this.description = newDescription;
        this.editedAt = currentDateTime;
    }
}
