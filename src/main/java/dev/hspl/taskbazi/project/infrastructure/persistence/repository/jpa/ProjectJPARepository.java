package dev.hspl.taskbazi.project.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectJPARepository extends JpaRepository<ProjectJPAEntity, UUID> {

}
