package dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.task.infrastructure.persistence.entity.TaskJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskJPARepository extends JpaRepository<TaskJPAEntity, UUID> {

}
