package dev.hspl.taskbazi.task.domain.service;

import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.task.domain.entity.Collaborator;
import dev.hspl.taskbazi.task.domain.entity.Project;
import dev.hspl.taskbazi.task.domain.entity.Task;
import dev.hspl.taskbazi.task.domain.exception.*;
import dev.hspl.taskbazi.task.domain.value.ProjectStatus;
import dev.hspl.taskbazi.task.domain.value.TaskPriority;
import dev.hspl.taskbazi.task.domain.value.TaskTitle;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TaskManagingService {

    public Task assignNewTask(
            LocalDateTime currentDateTime,
            UniversalUser assignerUser, // usually authenticated user (user who wants to assign a task)
            UUID newTaskId,
            Project targetProject,
            UniversalUser targetUser, // assignedTo
            TaskTitle taskTitle,
            Description taskDescription, // optional-nullable
            TaskPriority taskPriority,
            LocalDateTime dueDateTime // optional-nullable
    ) {
        boolean checkAccount = assignerUser.userRole().equals(UserRole.CLIENT) && assignerUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        boolean checkTargetAccount = targetUser.userRole().equals(UserRole.CLIENT) && targetUser.isAccountActive();
        if (!checkTargetAccount) {
            throw new UnsupportedTargetAccountTaskAssignmentException();
        }

        List<Collaborator> collaborators = targetProject.getCollaborators();

        UserId projectOwner = targetProject.getOwner();
        UserId assignerUserId = assignerUser.universalUserId();

        // first we should check the assignerUser is owner or at least a valid collaborator for assignment
        boolean isAssigningByOwner = projectOwner.equals(assignerUserId);
        if (!isAssigningByOwner) {
            boolean assigningByValidCollaborator = collaborators != null && collaborators.stream().anyMatch(collaborator -> {
                boolean collaboratorMatch = collaborator.getUserId().equals(assignerUserId);
                if (!collaboratorMatch) {
                    return false;
                }

                return collaborator.isActive() && (collaborator.isManagingAssignment() || collaborator.isSelfAssignment());
            });

            if (!assigningByValidCollaborator) {
                throw new InvalidUserTaskAssignmentException();
            }
        }

        // second we should check the project status
        boolean isProjectInProgress = targetProject.getStatus().equals(ProjectStatus.IN_PROGRESS);
        if (!isProjectInProgress) {
            throw new UnacceptableProjectStatusTaskAssignmentException();
        }

        // third we should check that target user should be owner or one ACTIVE collaborator
        UserId targetUserId = targetUser.universalUserId();
        boolean isTargetUserOwner = targetUserId.equals(projectOwner);
        if (!isTargetUserOwner) {
            boolean isTargetActiveCollaborator = collaborators != null && collaborators.stream()
                    .anyMatch(collaborator -> collaborator.getUserId().equals(targetUserId) && collaborator.isActive());

            if (!isTargetActiveCollaborator) {
                throw new InvalidTargetUserTaskAssignmentException();
            }
        }

        // fourth we should check that assigner is permitted to assign task to the target user!
        if (!isAssigningByOwner) {
            boolean isSelfAssignment = assignerUserId.equals(targetUserId);
            if (isSelfAssignment) {
                boolean allowSelfAssignment = collaborators != null && collaborators.stream()
                        .anyMatch(collaborator -> collaborator.getUserId().equals(assignerUserId) && collaborator.isSelfAssignment());
                if (!allowSelfAssignment) {
                    throw new SelfTaskAssignmentNotAllowedException();
                }
            } else {
                boolean allowManagingAssignment = collaborators != null && collaborators.stream()
                        .anyMatch(collaborator -> collaborator.getUserId().equals(assignerUserId) && collaborator.isManagingAssignment());
                if (!allowManagingAssignment) {
                    throw new ManagingTaskAssignmentNotAllowedException();
                }
            }
        }

        return Task.newTask(currentDateTime, newTaskId, targetProject.getId(), assignerUser,
                targetUser, taskTitle, taskDescription, taskPriority, dueDateTime, targetProject.getTitle());
    }
}
