package dev.hspl.taskbazi.project.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProjectJPARepository extends JpaRepository<ProjectJPAEntity, UUID> {
    @Query("SELECT p FROM Project p JOIN FETCH p.users WHERE p.id = :projectId")
    Optional<ProjectJPAEntity> findByIdWithUsers(
            @Param("projectId") UUID projectId
    ); // use entity graphs or join fetch clause to load users with project in one query(sql-join)
}
