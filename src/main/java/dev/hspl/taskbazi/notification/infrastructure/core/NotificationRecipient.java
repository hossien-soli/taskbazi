package dev.hspl.taskbazi.notification.infrastructure.core;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record NotificationRecipient(
        @NonNull UserRole userRole,
        @Nullable UserId userId, // required for some delivery agents
        @Nullable EmailAddress emailAddress // required for some delivery agents
) {
}
