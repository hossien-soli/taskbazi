package dev.hspl.taskbazi.notification.infrastructure.core.delivery;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

import java.util.Set;

// It detects the unique identifier of the application that the user uses for pushing notifications
// potential implementation -> DatabaseUserNativeApplicationDetector

public interface UserNativeApplicationDetector {
    Set<String> detectApplicationUniqueIdsForUser(
            UserRole userRole,
            UserId userId
    );
}
