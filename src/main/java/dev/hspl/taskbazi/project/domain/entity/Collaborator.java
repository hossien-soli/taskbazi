package dev.hspl.taskbazi.project.domain.entity;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.value.CollaboratorRole;
import lombok.Getter;

import java.time.LocalDateTime;

// owner can assign task to everyone
// normal collaborators(non-managing) can't assign task to others
// managingAssignment collaborators can assign task to others(even other managingAssignment collaborators)
// everyone can assign task to itself (by default)
// Inactive collaborators cannot take any actions and are only preserved for record-keeping
// Inactive collaborators will not receive any notifications regarding the project
// Inactive collaborators can see or track the project(read-only access)

@Getter
public class Collaborator {
    private final UserId userId;

    private CollaboratorRole role; // like Backend-Developer

    private boolean managingAssignment;
    private boolean selfAssignment; // can assign task to itself (default true)

    private boolean active;

    private final LocalDateTime joinedAt;

    private Collaborator(
            UserId userId,
            CollaboratorRole role,
            boolean managingAssignment,
            boolean selfAssignment,
            boolean active,
            LocalDateTime joinedAt
    ) {
        this.userId = userId;
        this.role = role;
        this.managingAssignment = managingAssignment;
        this.selfAssignment = selfAssignment;
        this.active = active;
        this.joinedAt = joinedAt;
    }

    public static Collaborator joinNewCollaborator(
            LocalDateTime currentDateTime,
            UserId userId,
            CollaboratorRole role,
            boolean managingAssignment
    ) {
        // TODO: add validation(null-check) for required fields
        return new Collaborator(userId,role,managingAssignment,true,true,currentDateTime);
    }

    public static Collaborator joinedCollaborator(
            UserId userId,
            CollaboratorRole role,
            boolean managingAssignment,
            boolean selfAssignment,
            boolean active,
            LocalDateTime joinedAt
    ) {
        // TODO: add validation(null-check) for required fields
        return new Collaborator(userId,role,managingAssignment,selfAssignment,active,joinedAt);
    }

    public void changeRole(CollaboratorRole newRole) {

    }
}
