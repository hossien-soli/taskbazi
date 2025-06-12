package dev.hspl.taskbazi.task.infrastructure.persistence.repository;

import dev.hspl.taskbazi.task.domain.entity.Task;
import dev.hspl.taskbazi.task.domain.repository.TaskRepository;
import dev.hspl.taskbazi.task.infrastructure.persistence.TaskModulePersistenceMapper;
import dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa.TaskJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLTaskRepository implements TaskRepository {
    private final TaskJPARepository jpaRepository;
    private final TaskModulePersistenceMapper mapper;

    @Override
    public void save(Task task) {
        jpaRepository.save(mapper.mapTaskToJPAEntity(task));
    }
}
