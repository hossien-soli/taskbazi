package dev.hspl.taskbazi.project.infrastructure.persistence.entity;

import dev.hspl.taskbazi.project.domain.value.TaskPriority;
import dev.hspl.taskbazi.project.domain.value.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Task")
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskJPAEntity {
    @Id
    @Column(nullable = false, name = "id", updatable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectJPAEntity project;

    @Column(nullable = true, name = "assigned_by", columnDefinition = "UUID")
    private UUID assignedByUserId; // null when user is deleted

    @Column(nullable = true, name = "assigned_to", columnDefinition = "UUID")
    private UUID assignedToUserId; // null when user is deleted

    @Column(nullable = false, name = "title", length = 45)
    private String title;

    @Column(nullable = true, name = "description", columnDefinition = "text")
    private String description;

    @Column(nullable = false, name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = true, name = "due")
    private LocalDateTime dueDateTime;

    @Column(nullable = true, name = "reject_reason", columnDefinition = "text")
    private String rejectReason;

    @Column(nullable = true, name = "cancel_reason", columnDefinition = "text")
    private String cancelReason;

    @Column(nullable = false, name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(nullable = true, name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(nullable = true, name = "started_at")
    private LocalDateTime startedAt;

    @Column(nullable = true, name = "completed_at")
    private LocalDateTime completedAt;

    @Column(nullable = true, name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(nullable = true, name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(nullable = true, name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(nullable = true, name = "version")
    @Version
    private Integer version;
}
