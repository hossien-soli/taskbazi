package dev.hspl.taskbazi.project.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY)
    private List<ProjectUser> users;

    @Column(nullable = true,name = "version")
    @Version
    private Integer version;
}
