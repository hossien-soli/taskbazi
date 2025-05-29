package dev.hspl.taskbazi.project.domain.service;

public interface ProjectManagingConstraints {
    short maxAllowedProjectInstance(); // 100 - as-owner or as-collaborator(doesn't matter)
    // The user must delete/leave old projects to create a new one if the maximum limit has been reached

    int projectRegistrationLimitationDelaySeconds(); // 10*60
    // delay between each project registration for a user
}
