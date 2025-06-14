package dev.hspl.taskbazi.task.domain.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.task.domain.entity.Project;
import dev.hspl.taskbazi.task.domain.value.ProjectId;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProjectRepository {
    Optional<LocalDateTime> getLastProjectRegistrationOfUser(UserId userId);

    short numberOfUserProjectInstances(UserId userId); // owned & joined

    void save(Project project);

    Optional<Project> find(ProjectId id); // maybe we should add owner(user) match to this for better performance
}
