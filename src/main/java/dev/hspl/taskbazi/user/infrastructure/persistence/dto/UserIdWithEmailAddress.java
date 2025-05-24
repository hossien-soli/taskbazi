package dev.hspl.taskbazi.user.infrastructure.persistence.dto;

import java.util.UUID;

public record UserIdWithEmailAddress(
        UUID userId,
        String emailAddress
) {
}
