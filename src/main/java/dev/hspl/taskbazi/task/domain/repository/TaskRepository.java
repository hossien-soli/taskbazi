package dev.hspl.taskbazi.task.domain.repository;

import dev.hspl.taskbazi.task.domain.entity.Task;

public interface TaskRepository {
    void save(Task task);
}
