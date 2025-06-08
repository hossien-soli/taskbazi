package dev.hspl.taskbazi.project.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.exception.InvalidUserIdException;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.application.exception.InvalidProjectIdException;
import dev.hspl.taskbazi.project.application.usage.write.TaskAssignmentUseCase;
import dev.hspl.taskbazi.project.application.usage.write.cmd.TaskAssignmentCommand;
import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.entity.Task;
import dev.hspl.taskbazi.project.domain.exception.UnsupportedTargetAccountTaskAssignmentException;
import dev.hspl.taskbazi.project.domain.repository.ProjectRepository;
import dev.hspl.taskbazi.project.domain.repository.TaskRepository;
import dev.hspl.taskbazi.project.domain.service.TaskManagingService;
import dev.hspl.taskbazi.user.UniversalUserResolverAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskManagingApplicationService implements TaskAssignmentUseCase {
    private final TaskManagingService domainService;
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final GlobalDomainEventPublisher domainEventPublisher;
    private final UniversalUserResolverAPI userResolver;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public void execute(UniversalUser authenticatedUser, TaskAssignmentCommand command) {
        boolean checkAccount = authenticatedUser.userRole().equals(UserRole.CLIENT) && authenticatedUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        UniversalUser targetUser = userResolver.resolveOne(UserRole.CLIENT, command.targetUserId())
                .orElseThrow(InvalidUserIdException::new);

        boolean checkTargetAccount = targetUser.userRole().equals(UserRole.CLIENT) && targetUser.isAccountActive();
        if (!checkTargetAccount) {
            throw new UnsupportedTargetAccountTaskAssignmentException();
        }

        Project targetProject = projectRepository.find(command.targetProjectId())
                .orElseThrow(InvalidProjectIdException::new);

        Task resultTask = domainService.assignNewTask(timeProvider.currentDateTime(), authenticatedUser,
                uuidGenerator.generateNew(), targetProject, targetUser, command.taskTitle(),
                command.taskDescription(), command.taskPriority(), command.dueDateTime());

        taskRepository.save(resultTask);
        domainEventPublisher.publishAll(resultTask);
    }
}
