package dev.hspl.taskbazi.task.infrastructure.persistence.repository;

import dev.hspl.taskbazi.task.application.repository.ProjectFetchRepository;
import dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa.ProjectJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLProjectFetchRepository implements ProjectFetchRepository {
    private final ProjectJPARepository jpaRepository;


}
