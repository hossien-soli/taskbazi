package dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.task.infrastructure.persistence.entity.ProjectUser;
import dev.hspl.taskbazi.task.infrastructure.persistence.entity.ProjectUserId;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// only clients can have project!!!
// userId = clientId

public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserId> {
    @Query("SELECT pu.joinedAt FROM ProjectUser pu WHERE pu.id.userId = :userId AND pu.owner = true ORDER BY pu.joinedAt DESC")
    List<LocalDateTime> findLastProjectRegistrationDateTimesByUser(
            @Param("userId") UUID userId,
            Limit limit
    ); // for owners -> project.registered_at = pu.joined_at

    @Query("SELECT COUNT(pu.id) FROM ProjectUser pu WHERE pu.id.userId = :userId")
    short countNumberOfProjectInstancesByUser(
            @Param("userId") UUID userId
    );
}
