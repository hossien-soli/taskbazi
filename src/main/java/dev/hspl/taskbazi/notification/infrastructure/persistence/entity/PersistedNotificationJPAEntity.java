package dev.hspl.taskbazi.notification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "PersistedNotification")
@Table(name = "persisted_notifications")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistedNotificationJPAEntity implements Persistable<UUID> {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "user_id",updatable = false,columnDefinition = "UUID")
    @NotNull
    private UUID userId;

    @Column(nullable = false,name = "subject",length = 100)
    @NotBlank
    private String subject;

    @Column(nullable = false,name = "message",columnDefinition = "text")
    @NotBlank
    private String message;

    @Column(nullable = true,name = "html_message",columnDefinition = "text")
    private String htmlMessage;

    @Column(nullable = false,name = "important")
    private boolean importantNotification;

    @Column(nullable = false,name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(nullable = true,name = "read_at")
    private LocalDateTime readAt;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
