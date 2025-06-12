package dev.hspl.taskbazi.task.infrastructure.listener;

import dev.hspl.taskbazi.common.domain.event.UserFullNameUpdatedDomainEvent;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa.TaskJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskModuleDomainEventListener {
    private final TaskJPARepository taskJPARepository;

    @EventListener // should not be async for running inside the publisher active transaction (consistency between modules)
    public void handleUserFullNameUpdatedEvent(UserFullNameUpdatedDomainEvent event) {
        if (event.getUserRole().equals(UserRole.CLIENT)) {
            UUID targetUserId = event.getUserId().value();
            String newFullName = event.getNewFullName();

            taskJPARepository.updateAssignedByUserFullName(targetUserId,newFullName);
            taskJPARepository.updateAssignedToUserFullName(targetUserId,newFullName);
            // TODO: update both (assignedByUser/assignedToUser) in a single query using native SQL CASE ... WHEN ... THEN ... ELSE statements
        }
    }
}
