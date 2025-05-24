package dev.hspl.taskbazi.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserId implements Serializable {
    @Column(nullable = false,name = "project_id",columnDefinition = "UUID")
    private UUID projectId;

    @Column(nullable = false,name = "user_id",columnDefinition = "UUID")
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectUserId that)) return false;
        return Objects.equals(projectId, that.projectId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
