package dev.hspl.taskbazi.project.domain.repository;

import dev.hspl.taskbazi.project.domain.entity.Project;

public interface ProjectRepository {
    void save(Project project);
}
