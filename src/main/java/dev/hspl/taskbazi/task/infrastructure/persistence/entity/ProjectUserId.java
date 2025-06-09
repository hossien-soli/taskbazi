package dev.hspl.taskbazi.task.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ProjectUserId implements Serializable {
    @Column(nullable = false,name = "project_id",columnDefinition = "UUID")
    private UUID projectId;

    @Column(nullable = false,name = "user_id",columnDefinition = "UUID")
    private UUID userId;
}
