package dev.hspl.taskbazi.project.application.init;

import dev.hspl.taskbazi.project.domain.service.ProjectManagingConstraints;
import dev.hspl.taskbazi.project.domain.service.ProjectManagingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainServiceInitialization {
    @Bean
    public ProjectManagingService projectManagingServiceSingleton(
            ProjectManagingConstraints projectManagingConstraints
    ) {
        return new ProjectManagingService(projectManagingConstraints);
    }
}
