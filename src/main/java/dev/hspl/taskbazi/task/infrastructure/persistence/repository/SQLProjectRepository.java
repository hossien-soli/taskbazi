package dev.hspl.taskbazi.task.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.task.domain.entity.Project;
import dev.hspl.taskbazi.task.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.task.domain.value.ProjectId;
import dev.hspl.taskbazi.task.infrastructure.persistence.TaskModulePersistenceMapper;
import dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa.ProjectJPARepository;
import dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SQLProjectRepository implements ProjectRepository {
    private final ProjectJPARepository jpaRepository;
    private final ProjectUserRepository userAwareRepository;
    private final TaskModulePersistenceMapper mapper;

    @Override
    public Optional<LocalDateTime> getLastProjectRegistrationOfUser(UserId userId) {
        List<LocalDateTime> fetchResult = userAwareRepository.findLastProjectRegistrationDateTimesByUser(
                userId.value(),
                Limit.of(1)
        );

        return fetchResult.stream().findFirst();
    }

    @Override
    public short numberOfUserProjectInstances(UserId userId) {
        return userAwareRepository.countNumberOfProjectInstancesByUser(userId.value());
    }

    @Override
    public void save(Project project) {
        jpaRepository.save(mapper.mapProjectToJPAEntity(project));
    }

    @Override
    public Optional<Project> find(ProjectId id) {
        return jpaRepository.findByIdWithUsers(id.value()).map(mapper::mapJPAEntityToProject);
    }
}
