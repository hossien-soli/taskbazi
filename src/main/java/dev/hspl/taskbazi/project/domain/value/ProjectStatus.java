package dev.hspl.taskbazi.project.domain.value;

public enum ProjectStatus {
    REGISTERED, // registered in taskbazi
    IN_PROGRESS, // started
    ARCHIVED, // temporarily closed (can start again)
    COMPLETED, // closed (can not start again)
    CANCELLED, // closed (can not start again)
    BLOCKED // closed - blocked by a moderator or admin (can not start again)
}
