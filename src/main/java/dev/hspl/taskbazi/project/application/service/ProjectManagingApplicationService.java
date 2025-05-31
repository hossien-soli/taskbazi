package dev.hspl.taskbazi.project.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.RegisterProjectUseCase;
import dev.hspl.taskbazi.project.application.usage.cmd.RegisterProjectCommand;
import dev.hspl.taskbazi.project.application.usage.result.RegisterProjectResult;
import dev.hspl.taskbazi.project.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.project.domain.service.ProjectManagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectManagingApplicationService implements RegisterProjectUseCase {
    private final ProjectManagingService domainService;
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final GlobalDomainEventPublisher domainEventPublisher;
    private final ProjectRepository projectRepository;

    @Override
    public RegisterProjectResult execute(
            UniversalUser authenticatedUser,
            RegisterProjectCommand command
    ) {

        return null;
    }
}
