package dev.hspl.taskbazi.project.infrastructure.persistence.repository;

import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.project.infrastructure.persistence.ProjectModulePersistenceMapper;
import dev.hspl.taskbazi.project.infrastructure.persistence.repository.jpa.ProjectJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLProjectRepository implements ProjectRepository {
    private final ProjectJPARepository jpaRepository;
    private final ProjectModulePersistenceMapper mapper;

    @Override
    public void save(Project project) {
        jpaRepository.save(mapper.mapProjectToJPAEntity(project));
    }
}
