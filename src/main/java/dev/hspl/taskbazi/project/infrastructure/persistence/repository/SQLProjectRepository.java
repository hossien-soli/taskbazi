package dev.hspl.taskbazi.project.infrastructure.persistence.repository;

import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLProjectRepository implements ProjectRepository {

    @Override
    public void save(Project project) {

    }
}
