package dev.hspl.taskbazi.project.domain.repository;

import dev.hspl.taskbazi.project.domain.entity.Project;

public interface ProjectRepository {
    void save(Project project);
}

//is putting project instance counter inside this repository is oK!!????
//how about last project creation detection
