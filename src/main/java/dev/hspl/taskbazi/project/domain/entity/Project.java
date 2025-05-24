package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationBroadcastEvent;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.event.ProjectStartedDomainEvent;
import dev.hspl.taskbazi.project.domain.exception.ProjectIsNotEditableException;
import dev.hspl.taskbazi.project.domain.exception.ProjectIsNotStartableException;
import dev.hspl.taskbazi.project.domain.value.ProjectDescription;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectStatus;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Project extends DomainAggregateRoot {
    private final ProjectId id;
    private final UserId owner; // maybe in the future changing the owner of project become possible!

    private ProjectTitle title;
    private ProjectDescription description; // nullable - check for cross-site scripting in the infrastructure!!!

    private ProjectStatus status;

    private final LocalDateTime registeredAt;
    private LocalDateTime startedAt; // nullable
    private LocalDateTime closedAt; // nullable | Archived or Completed or Cancelled or Blocked
    private LocalDateTime editedAt;

    private final List<Collaborator> collaborators; // nullable or empty list (final for only reference)

    private final Integer version; // just a data transfer property

    private Project(
            ProjectId id,
            UserId owner,
            ProjectTitle title,
            ProjectDescription description,
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
            UserId owner,
            ProjectTitle title,
            ProjectDescription description // nullable
    ) {
        // TODO: add validation(null-check) for required fields
        return new Project(newProjectId,owner,title,description,ProjectStatus.REGISTERED,currentDateTime,
                null,null,null,null,null);
    }

    public static Project existingProject(
            ProjectId id,
            UserId owner,
            ProjectTitle title,
            ProjectDescription description,
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

        DomainNotificationBroadcastEvent domainEvent = new ProjectStartedDomainEvent(
                currentDateTime,
                this.id,
                this.title,
                this.owner,
                this.collaborators
        );

        registerDomainEvent(domainEvent);
    }

    public void archiveProject(LocalDateTime currentDateTime) {

    }

    public void completeProject(LocalDateTime currentDateTime) {

    }

    public void cancelProject(LocalDateTime currentDateTime) {

    }

    public void blockProject(LocalDateTime currentDateTime) {

    }

    public void edit(
            LocalDateTime currentDateTime,
            ProjectTitle newTitle,
            ProjectDescription newDescription
    ) {
        boolean editable = this.status.equals(ProjectStatus.REGISTERED) || this.status.equals(ProjectStatus.IN_PROGRESS)
                || this.status.equals(ProjectStatus.ARCHIVED);
        if (!editable) {
            throw new ProjectIsNotEditableException();
        }

        // TODO: add null-check for value objects
        this.title = newTitle;
        this.description = newDescription;
        this.editedAt = currentDateTime;
    }
}
