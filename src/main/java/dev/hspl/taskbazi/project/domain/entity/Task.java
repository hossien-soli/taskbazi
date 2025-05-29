package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserNote;
import dev.hspl.taskbazi.project.domain.value.TaskPriority;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.TaskStatus;
import dev.hspl.taskbazi.project.domain.value.TaskTitle;
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

    private final UserId assignedBy; // not-optional but can be null if the user leave the project
    private final UserId assignedTo; // not-optional but can be null if the user leave the project

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
    private LocalDateTime canceledAt;   // nullable
    private LocalDateTime rejectedAt;   // nullable
    private LocalDateTime verifiedAt;   // nullable

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
            LocalDateTime canceledAt,
            LocalDateTime rejectedAt,
            LocalDateTime verifiedAt
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
        this.canceledAt = canceledAt;
        this.rejectedAt = rejectedAt;
        this.verifiedAt = verifiedAt;
    }

    public static Task assignNewTask(
            LocalDateTime currentDateTime,
            UUID newTaskId,
            ProjectId projectId,
            UserId assignedBy,
            UserId assignedTo,
            TaskTitle title,
            Description description,
            TaskPriority priority,
            LocalDateTime dueDateTime
    ) {

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
            LocalDateTime canceledAt,
            LocalDateTime rejectedAt,
            LocalDateTime verifiedAt
    ) {
        return new Task(id,projectId,assignedBy,assignedTo,title,description,priority,status,dueDateTime,rejectReason,
                cancelReason,assignedAt,acceptedAt,startedAt,completedAt,canceledAt,rejectedAt,verifiedAt);
    }
}
