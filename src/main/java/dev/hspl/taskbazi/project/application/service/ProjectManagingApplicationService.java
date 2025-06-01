package dev.hspl.taskbazi.project.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.application.usage.RegisterProjectUseCase;
import dev.hspl.taskbazi.project.application.usage.cmd.RegisterProjectCommand;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.project.domain.service.ProjectManagingService;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public void execute(
            UniversalUser authenticatedUser,
            RegisterProjectCommand command
    ) {
        boolean checkAccount = authenticatedUser.userRole().equals(UserRole.CLIENT) && authenticatedUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        LocalDateTime currentDateTime = timeProvider.currentDateTime();
        ProjectId newProjectId = new ProjectId(uuidGenerator.generateNew());

        UserId userId = authenticatedUser.universalUserId();

        Project project = domainService.registerProjectForUser(
                currentDateTime,
                authenticatedUser,
                newProjectId,
                command.projectTitle(),
                command.projectDescription(),
                projectRepository.getLastProjectRegistrationOfUser(userId).orElse(null),
                projectRepository.numberOfUserProjectInstances(userId)
        );

        projectRepository.save(project);
        domainEventPublisher.publishAll(project);
    }
}
