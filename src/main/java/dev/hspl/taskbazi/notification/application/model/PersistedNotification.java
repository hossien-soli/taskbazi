package dev.hspl.taskbazi.notification.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PersistedNotification {
    private final UUID id;
    private final UUID userId;
    private final String subject;
    private final String message;
    private final String htmlMessage;
    private final boolean importantNotification;
    private final LocalDateTime createdAt;
    private LocalDateTime readAt; // not-final & updatable

    public static PersistedNotification newNotification(
            @NonNull UUID userId, @NonNull String subject, @NonNull String message,
            @Nullable String htmlMessage, boolean importantNotification
    ) {
        return new PersistedNotification(UUID.randomUUID(),userId,subject,message,htmlMessage,
                importantNotification,LocalDateTime.now(),null);
    }

    public static PersistedNotification existingNotification(
            UUID id,
            UUID userId,
            String subject,
            String message,
            String htmlMessage,
            boolean importantNotification,
            LocalDateTime createdAt,
            LocalDateTime readAt
    ) {
        return new PersistedNotification(id,userId,subject,message,htmlMessage,
                importantNotification,createdAt,readAt);
    }

    public void markAsRead() {
        this.readAt = LocalDateTime.now();
    }
}
