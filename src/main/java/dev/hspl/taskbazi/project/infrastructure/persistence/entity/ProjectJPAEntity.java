package dev.hspl.taskbazi.project.infrastructure.persistence.entity;

import dev.hspl.taskbazi.project.domain.value.ProjectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "Project")
@Table(name = "projects")
@Getter
@Setter
public class ProjectJPAEntity {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "title",length = 50)
    private String title;

    @Column(nullable = true,name = "description",columnDefinition = "text")
    private String description;

    @Column(nullable = false,name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = false,name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(nullable = true,name = "started_at")
    private LocalDateTime startedAt;

    @Column(nullable = true,name = "closed_at")
    private LocalDateTime closedAt;

    @Column(nullable = true,name = "edited_at")
    private LocalDateTime editedAt;

    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectUser> users;

    @Column(nullable = true,name = "version")
    @Version
    private Integer version; // check for optimistic concurrency control with front-end clients in the application layer
}
