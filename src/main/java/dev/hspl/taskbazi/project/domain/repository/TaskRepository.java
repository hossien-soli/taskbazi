package dev.hspl.taskbazi.project.domain.repository;

import dev.hspl.taskbazi.project.domain.entity.Task;

public interface TaskRepository {
    void save(Task task);
}
