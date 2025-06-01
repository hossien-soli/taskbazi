package dev.hspl.taskbazi.project.domain.service;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.exception.ProjectRegistrationRestrictionException;
import dev.hspl.taskbazi.project.domain.exception.TooManyProjectInstanceException;
import dev.hspl.taskbazi.common.domain.exception.UnsupportedAccountException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProjectManagingService {
    private final ProjectManagingConstraints constraints;

    public Project registerProjectForUser(
            LocalDateTime currentDateTime,
            UniversalUser authenticatedUser,
            ProjectId newProjectId,
            ProjectTitle projectTitle,
            Description projectDescription, // nullable
            LocalDateTime lastUserProjectRegistrationTime, // nullable (null=no previous project)
            short numberOfUserCurrentProjectInstances // owned & joined projects
    ) {
        boolean checkAccount = authenticatedUser.userRole().equals(UserRole.CLIENT) && authenticatedUser.isAccountActive();
        if (!checkAccount) {
            throw new UnsupportedAccountException();
        }

        short maxAllowedProjectInstance = constraints.maxAllowedProjectInstance();
        boolean canHaveMoreInstance = numberOfUserCurrentProjectInstances < maxAllowedProjectInstance;
        if (!canHaveMoreInstance) {
            throw new TooManyProjectInstanceException(maxAllowedProjectInstance);
        }

        boolean hasPreviousProject = lastUserProjectRegistrationTime != null;
        if (hasPreviousProject) {
            int registrationLimitationDelaySeconds = constraints.projectRegistrationLimitationDelaySeconds();
            long secondsElapsed = Math.abs(Duration.between(currentDateTime, lastUserProjectRegistrationTime).toSeconds());
            boolean canCreateRightNow = secondsElapsed >= registrationLimitationDelaySeconds;
            if (!canCreateRightNow) {
                throw new ProjectRegistrationRestrictionException(
                        registrationLimitationDelaySeconds,
                        registrationLimitationDelaySeconds - secondsElapsed
                );
            }
        }

        return Project.registerNewProject(currentDateTime, newProjectId, authenticatedUser.universalUserId(),
                projectTitle, projectDescription);
    }

    public void authorizeProjectDeletion(
            UniversalUser authenticatedUser,
            Project project
    ) {
        // check user can delete this project
        // user must be the owner of the project for deleting it
        // allowed status for deletion ARCHIVED, COMPLETED, CANCELLED
        // user can't delete BLOCKED projects
        // a delay between creation and deleting the project should set: 1H
    }
}
