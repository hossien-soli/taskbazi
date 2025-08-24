package dev.hspl.taskbazi.task.application.dto;

import dev.hspl.taskbazi.task.domain.value.ProjectStatus;

import java.time.LocalDateTime;
import java.util.UUID;

// an object for representing project with membership state of a user
// this uses for fetching all projects of a user(with user memebership details)

public record MembershipProjectDTO(
        UUID id,
        String title,
        String description,
        ProjectStatus status,
        LocalDateTime registeredAt,
        LocalDateTime startedAt,
        LocalDateTime closedAt,
        LocalDateTime editedAt,
        int _version,
        MembershipDetailsDTO membershipDetails
) {
}
