package dev.hspl.taskbazi.project.domain.value;

// closed-task = VERIFIED or CANCELLED or REJECTED

public enum TaskStatus {
    ASSIGNED, // assigned to a collaborator by owner,managingAssignment collaborator or self-assignment (open)
    ACCEPTED, // accepted by assignedTo collaborator(user) / cannot reject later (open)
    IN_PROGRESS, // assignedTo started the task (open)
    COMPLETED, // assignedTo completed the task (open)
    VERIFIED, // owner or assignedBy verified the task (closed)
    CANCELLED, // owner or assignedBy cancelled the task (closed)
    REJECTED // assignedTo rejected the task!! not-accepted! / cannot accept later!!! (closed)
}
