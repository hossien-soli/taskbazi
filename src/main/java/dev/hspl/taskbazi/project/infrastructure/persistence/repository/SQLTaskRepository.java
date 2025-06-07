package dev.hspl.taskbazi.project.infrastructure.persistence.repository;

import dev.hspl.taskbazi.project.domain.entity.Task;
import dev.hspl.taskbazi.project.domain.repository.TaskRepository;
import dev.hspl.taskbazi.project.infrastructure.persistence.ProjectModulePersistenceMapper;
import dev.hspl.taskbazi.project.infrastructure.persistence.repository.jpa.TaskJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLTaskRepository implements TaskRepository {
    private final TaskJPARepository jpaRepository;
    private final ProjectModulePersistenceMapper mapper;

    @Override
    public void save(Task task) {
        jpaRepository.save(mapper.mapTaskToJPAEntity(task));
    }
}
