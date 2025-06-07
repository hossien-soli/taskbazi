package dev.hspl.taskbazi.project.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.project.infrastructure.persistence.entity.TaskJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskJPARepository extends JpaRepository<TaskJPAEntity, UUID> {

}
