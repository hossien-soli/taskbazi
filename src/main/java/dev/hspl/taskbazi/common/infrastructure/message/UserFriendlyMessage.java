package dev.hspl.taskbazi.common.infrastructure.message;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record UserFriendlyMessage(
        @NonNull String subject,
        @NonNull String plainTextBody,
        @Nullable String htmlBody,
        boolean isImportant
) {
}
