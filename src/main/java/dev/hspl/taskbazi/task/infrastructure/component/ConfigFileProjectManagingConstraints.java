package dev.hspl.taskbazi.task.infrastructure.component;

import dev.hspl.taskbazi.task.domain.service.ProjectManagingConstraints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// potential implementations: DatabaseProjectManagingConstraints, ConfigFileProjectManagingConstraints
// or ConfigServerProjectManagingConstraints

@Component
public class ConfigFileProjectManagingConstraints implements ProjectManagingConstraints {
    @Value("${custom.domain.project.max_allowed_project_instance}")
    private short maxAllowedProjectInstance;

    @Value("${custom.domain.project.registration_limitation_delay_seconds}")
    private int projectRegistrationLimitationDelaySeconds;

    @Override
    public short maxAllowedProjectInstance() {
        return maxAllowedProjectInstance;
    }

    @Override
    public int projectRegistrationLimitationDelaySeconds() {
        return projectRegistrationLimitationDelaySeconds;
    }
}
