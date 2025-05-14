package dev.hspl.taskbazi.common.infrastructure.persistence.entity;

import dev.hspl.taskbazi.common.infrastructure.persistence.enums.OutboxEmailStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// use pessimistic locking for preventing duplicate send
// use pessimistic locking for preventing duplicate send

@Entity
@Table(name = "outbox_email_records")
@Getter
@Setter
@ToString
public class OutboxEmailRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "email",length = 60)
    @NotBlank
    private String targetEmailAddress;

    @Column(nullable = false,name = "subject",length = 100)
    @NotBlank
    private String messageSubject;

    @Column(nullable = false,name = "simple_message",columnDefinition = "text")
    @NotBlank
    private String messageSimpleBody;

    @Column(nullable = true,name = "html_message",columnDefinition = "text")
    private String messageHTMLBody;

    @Column(nullable = false,name = "important_message")
    private boolean messageIsImportant = false;

    @Column(nullable = false,name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private OutboxEmailStatus status = OutboxEmailStatus.CREATED;

    @Column(nullable = false,name = "attempts")
    private short numberOfAttempts = 0;

    @Column(nullable = false,name = "created_at",updatable = false)
    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;
}
