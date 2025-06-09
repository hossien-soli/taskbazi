package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.event.ManagingTaskAssignmentDomainEvent;
import dev.hspl.taskbazi.common.domain.exception.MissingUserNoteException;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.project.domain.exception.UnsupportedTargetAccountTaskAssignmentException;
import dev.hspl.taskbazi.project.domain.value.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

// users can accept & reject tasks if the task is assigned by another user
// self-assigned tasks are not acceptable or rejectable!!! (if assignedBy == assignedTo -> self-assignment task)
// only assignedTo user can accept,in_progress,complete,reject tasks
// only owner and assignedBy can verify the task (also cancel the task)
// assigned_to user can't cancel/verify the task
// only owner and assignedBy can delete the task
// closed-task = VERIFIED or CANCELLED or REJECTED
// non-closed tasks can be deleted

@Getter
public class Task extends DomainAggregateRoot {
    private final UUID id;
    private final ProjectId projectId;

    private final UserId assignedBy; // not-optional but can be null if the user leave the project or deleted
    private final UserId assignedTo; // not-optional but can be null if the user leave the project or deleted
    // deletion or leave of user cannot cause task deletion just the value set to null!!

    // maybe we need to add assignedBy & assignedTo full names to this entity

    private TaskTitle title;
    private Description description; // optional-nullable
    private TaskPriority priority;

    private TaskStatus status;

    private LocalDateTime dueDateTime; // optional-nullable - task must be completed at this date-time

    private UserNote rejectReason; // nullable - reject reason by assigned_to user
    private UserNote cancelReason; // nullable

    private final LocalDateTime assignedAt;
    private LocalDateTime acceptedAt;   // nullable
    private LocalDateTime startedAt;    // nullable
    private LocalDateTime completedAt;  // nullable
    private LocalDateTime cancelledAt;   // nullable
    private LocalDateTime rejectedAt;   // nullable
    private LocalDateTime verifiedAt;   // nullable

    private final Integer version; // just a data transfer property

    private Task(
            UUID id,
            ProjectId projectId,
            UserId assignedBy,
            UserId assignedTo,
            TaskTitle title,
            Description description,
            TaskPriority priority,
            TaskStatus status,
            LocalDateTime dueDateTime,
            UserNote rejectReason,
            UserNote cancelReason,
            LocalDateTime assignedAt,
            LocalDateTime acceptedAt,
            LocalDateTime startedAt,
            LocalDateTime completedAt,
            LocalDateTime cancelledAt,
            LocalDateTime rejectedAt,
            LocalDateTime verifiedAt,
            Integer version
    ) {
        this.id = id;
        this.projectId = projectId;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDateTime = dueDateTime;
        this.rejectReason = rejectReason;
        this.cancelReason = cancelReason;
        this.assignedAt = assignedAt;
        this.acceptedAt = acceptedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.cancelledAt = cancelledAt;
        this.rejectedAt = rejectedAt;
        this.verifiedAt = verifiedAt;
        this.version = version;
    }

    public static Task newTask(
            LocalDateTime currentDateTime,
            UUID newTaskId,
            ProjectId projectId,
            UniversalUser assignerUser, // assignedByUser
            UniversalUser targetUser, // assignedToUser
            TaskTitle title,
            Description description, // optional-nullable
            TaskPriority priority,
            LocalDateTime dueDateTime, // optional-nullable
            ProjectTitle relatedProjectTitle  // needed for domain notification event
    ) {
        boolean checkAccount = assignerUser.userRole().equals(UserRole.CLIENT) && assignerUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        boolean checkTargetAccount = targetUser.userRole().equals(UserRole.CLIENT) && targetUser.isAccountActive();
        if (!checkTargetAccount) {
            throw new UnsupportedTargetAccountTaskAssignmentException();
        }

        TaskStatus newTaskStatus = TaskStatus.ASSIGNED;
        LocalDateTime acceptedAt = null;
        boolean isSelfAssignment = assignerUser.universalUserId().equals(targetUser.universalUserId());
        if (isSelfAssignment) {
            newTaskStatus = TaskStatus.ACCEPTED;
            acceptedAt = currentDateTime;
        }

        Task result = new Task(newTaskId, projectId, assignerUser.universalUserId(), targetUser.universalUserId(),
                title, description, priority, newTaskStatus, dueDateTime, null, null, currentDateTime, acceptedAt,
                null, null, null, null, null, null);

        if (!isSelfAssignment) {
            DomainNotificationRequestEvent event = new ManagingTaskAssignmentDomainEvent(
                    currentDateTime, targetUser.universalUserId(), targetUser.universalUserEmailAddress(),
                    projectId.value(), relatedProjectTitle.value(), title.value(), priority.toString()
            );

            result.registerDomainEvent(event);
        }

        return result;
    }

    public static Task existingTask(
            UUID id,
            ProjectId projectId,
            UserId assignedBy,
            UserId assignedTo,
            TaskTitle title,
            Description description,
            TaskPriority priority,
            TaskStatus status,
            LocalDateTime dueDateTime,
            UserNote rejectReason,
            UserNote cancelReason,
            LocalDateTime assignedAt,
            LocalDateTime acceptedAt,
            LocalDateTime startedAt,
            LocalDateTime completedAt,
            LocalDateTime cancelledAt,
            LocalDateTime rejectedAt,
            LocalDateTime verifiedAt,
            Integer version
    ) {
        return new Task(id, projectId, assignedBy, assignedTo, title, description, priority, status, dueDateTime, rejectReason,
                cancelReason, assignedAt, acceptedAt, startedAt, completedAt, cancelledAt, rejectedAt, verifiedAt, version);
    }

    public void acceptTask(
            LocalDateTime currentDateTime,
            UniversalUser acceptorUser  // user who wants to accept this task
    ) {
        boolean checkAccount = acceptorUser.userRole().equals(UserRole.CLIENT) && acceptorUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        boolean checkUser = acceptorUser.universalUserId().equals(this.assignedTo);
        if (!checkUser) {

        }

        boolean checkStatus = this.status.equals(TaskStatus.ASSIGNED);
        if (!checkStatus) {

        }

        this.status = TaskStatus.ACCEPTED;
        this.acceptedAt = currentDateTime;
    }

    public void rejectTask(
            LocalDateTime currentDateTime,
            UniversalUser rejecterUser,  // user who wants to reject this task
            UserNote rejectReason // not-null / required
    ) {
        if (rejectReason == null) {
            throw new MissingUserNoteException();
        }

        boolean checkAccount = rejecterUser.userRole().equals(UserRole.CLIENT) && rejecterUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        boolean checkUser = rejecterUser.universalUserId().equals(this.assignedTo);
        if (!checkUser) {

        }

        boolean checkStatus = this.status.equals(TaskStatus.ASSIGNED);
        if (!checkStatus) {

        }

        this.status = TaskStatus.REJECTED;
        this.rejectedAt = currentDateTime;
        this.rejectReason = rejectReason;
    }
}
