package dev.hspl.taskbazi.task.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.exception.ResourceVersionConflictException;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.task.application.exception.InvalidProjectIdException;
import dev.hspl.taskbazi.task.application.usage.write.CloseProjectUseCase;
import dev.hspl.taskbazi.task.application.usage.write.StartProjectUseCase;
import dev.hspl.taskbazi.task.application.usage.write.RegisterProjectUseCase;
import dev.hspl.taskbazi.task.application.usage.write.cmd.CloseProjectCommand;
import dev.hspl.taskbazi.task.application.usage.write.cmd.RegisterProjectCommand;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.task.application.usage.write.cmd.StartProjectCommand;
import dev.hspl.taskbazi.task.domain.entity.Project;
import dev.hspl.taskbazi.task.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.task.domain.service.ProjectManagingService;
import dev.hspl.taskbazi.task.domain.value.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectManagingApplicationService implements RegisterProjectUseCase, StartProjectUseCase, CloseProjectUseCase {
    private final ProjectManagingService domainService;
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final GlobalDomainEventPublisher domainEventPublisher;
    private final ProjectRepository projectRepository;

    @Override
    public void execute(UniversalUser authenticatedUser, RegisterProjectCommand command) {
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

    @Override
    public void execute(UniversalUser authenticatedUser, StartProjectCommand command) {
        boolean checkAccount = authenticatedUser.userRole().equals(UserRole.CLIENT) && authenticatedUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        Project project = projectRepository.find(command.projectId())
                .orElseThrow(InvalidProjectIdException::new);
        // maybe we should check the ownership of project here in query(more performance)

        // check version with client
        boolean versionMatch = Objects.equals(project.getVersion(),command.clientResourceVersion());
        if (!versionMatch) {
            throw new ResourceVersionConflictException();
        }

        domainService.tryStartProject(
                timeProvider.currentDateTime(),
                authenticatedUser,
                project
        );

        projectRepository.save(project); // handle OptimisticLockException using @RestControllerAdvice & @ExceptionHandler
        domainEventPublisher.publishAll(project);
    }

    @Override
    public void execute(UniversalUser authenticatedUser, CloseProjectCommand command) {

    }
}
