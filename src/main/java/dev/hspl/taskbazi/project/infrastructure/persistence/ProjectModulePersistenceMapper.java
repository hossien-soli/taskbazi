package dev.hspl.taskbazi.project.infrastructure.persistence;

import dev.hspl.taskbazi.project.domain.entity.Collaborator;
import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectJPAEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectModulePersistenceMapper {

    public ProjectJPAEntity mapProjectToJPAEntity(Project project) {
        ProjectJPAEntity result = new ProjectJPAEntity();
        result.setId(project.getId().value());
        result.setTitle(project.getTitle().value());
        result.setDescription(project.getDescription() == null ? null : project.getDescription().value());
        result.setStatus(project.getStatus());
        result.setRegisteredAt(project.getRegisteredAt());
        result.setStartedAt(project.getStartedAt());
        result.setClosedAt(project.getClosedAt());
        result.setEditedAt(project.getEditedAt());


        List<Collaborator> collaborators = project.getCollaborators();
        if (collaborators != null && !collaborators.isEmpty()) {

        }

        result.setVersion(project.getVersion());
    }
}
