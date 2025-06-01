package dev.hspl.taskbazi.project.domain.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.entity.Project;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProjectRepository {
    Optional<LocalDateTime> getLastProjectRegistrationOfUser(UserId userId);

    short numberOfUserProjectInstances(UserId userId); // owned & joined

    void save(Project project);
}

//is putting project instance counter inside this repository is oK!!????
//how about last project creation detection
