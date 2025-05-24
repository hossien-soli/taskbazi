package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.value.CollaboratorRole;
import lombok.Getter;

import java.time.LocalDateTime;

// Only owner can assign task to managingAssignment collaborators!
// managingAssignment collaborators can assign task to non-managingAssignment collaborators
// everyone can assign task to itself (by default)
// Inactive collaborators cannot take any actions and are only preserved for record-keeping
// Inactive collaborators will not receive any notifications regarding the project

@Getter
public class Collaborator {
    private final UserId userId;

    private CollaboratorRole role; // like Backend-Developer

    private boolean managingAssignment; // can assign task to non-managingAssignment collaborators
    private boolean selfAssignment; // can assign task to itself

    private boolean active;

    private final LocalDateTime joinedAt;
    // maybe a date-time field for capturing last activity of collaborator

    private final Short version; // just a data transfer property

    private Collaborator(
            UserId userId,
            CollaboratorRole role,
            boolean managingAssignment,
            boolean selfAssignment,
            boolean active,
            LocalDateTime joinedAt,
            Short version
    ) {
        this.userId = userId;
        this.role = role;
        this.managingAssignment = managingAssignment;
        this.selfAssignment = selfAssignment;
        this.active = active;
        this.joinedAt = joinedAt;
        this.version = version;
    }

    public void changeRole(CollaboratorRole newRole) {

    }
}
