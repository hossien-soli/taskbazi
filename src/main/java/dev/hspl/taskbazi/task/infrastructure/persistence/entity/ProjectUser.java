package dev.hspl.taskbazi.task.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// only clients can have project!!!
// userId = clientId

@Entity
@Table(name = "projects_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectUser {
    @EmbeddedId
    private ProjectUserId id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    private ProjectJPAEntity project;

    @Column(nullable = false,name = "owner")
    private boolean owner;

    @Column(nullable = false,name = "role",length = 50)
    private String role;

    @Column(nullable = false,name = "managing_assign")
    private boolean managingAssignment;

    @Column(nullable = false,name = "self_assign")
    private boolean selfAssignment;

    @Column(nullable = false,name = "active")
    private boolean active;

    @Column(nullable = false,name = "joined_at")
    private LocalDateTime joinedAt; // for owners -> project.registered_at = joined_at
}
