package dev.hspl.taskbazi.project.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.InvalidUserIdException;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.application.usage.TaskAssignmentUseCase;
import dev.hspl.taskbazi.project.application.usage.cmd.TaskAssignmentCommand;
import dev.hspl.taskbazi.project.domain.service.TaskManagingService;
import dev.hspl.taskbazi.user.UniversalUserResolver;
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
    private final UniversalUserResolver userResolver;

    @Override
    public void execute(UniversalUser authenticatedUser, TaskAssignmentCommand command) {

        UniversalUser targetUser = userResolver.resolveOne(UserRole.CLIENT, command.targetUserId())
                .orElseThrow(InvalidUserIdException::new);

    }
}
