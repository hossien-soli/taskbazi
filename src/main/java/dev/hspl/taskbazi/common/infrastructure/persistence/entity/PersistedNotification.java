package dev.hspl.taskbazi.common.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "persisted_notifications")
@Getter
@Setter
@ToString
public class PersistedNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private String htmlMessage = null;

    @Column(nullable = false,name = "important")
    private boolean importantNotification = false;

    @Column(nullable = false,name = "created_at",updatable = false)
    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @Column(nullable = true,name = "read_at",insertable = false)
    private LocalDateTime readAt = null;
}
