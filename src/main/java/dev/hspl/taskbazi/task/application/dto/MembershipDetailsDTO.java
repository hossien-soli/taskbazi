package dev.hspl.taskbazi.task.application.dto;

import java.time.LocalDateTime;

public record MembershipDetailsDTO(
        boolean isOwner,
        String role,
        boolean managingAssignmentPermission,
        boolean selfAssignmentPermission,
        boolean activeOnProject,
        LocalDateTime joinedAt
) {
}
