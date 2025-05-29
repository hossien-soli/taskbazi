package dev.hspl.taskbazi.project.infrastructure.persistence;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.entity.Collaborator;
import dev.hspl.taskbazi.project.domain.entity.Project;
import dev.hspl.taskbazi.project.domain.value.CollaboratorRole;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectJPAEntity;
import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectUser;
import dev.hspl.taskbazi.project.infrastructure.persistence.entity.ProjectUserId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        List<ProjectUser> users = new ArrayList<>(10);

        ProjectUserId ownerProjectUserId = ProjectUserId.builder()
                .projectId(project.getId().value())
                .userId(project.getOwner().value())
                .build();

        ProjectUser ownerProjectUser = ProjectUser.builder()
                .id(ownerProjectUserId)
                .project(result)
                .owner(true)
                .role("OWNER")
                .managingAssignment(true)
                .selfAssignment(true)
                .active(true)
                .joinedAt(project.getRegisteredAt())
                .build();

        users.add(ownerProjectUser);

        List<Collaborator> collaborators = project.getCollaborators();
        if (collaborators != null) {
            for (Collaborator collaborator : collaborators) {
                ProjectUserId currentProjectUserId = ProjectUserId.builder()
                        .projectId(project.getId().value())
                        .userId(collaborator.getUserId().value())
                        .build();

                ProjectUser currentProjectUser = ProjectUser.builder()
                        .id(currentProjectUserId)
                        .project(result)
                        .owner(false)
                        .role(collaborator.getRole().value())
                        .managingAssignment(collaborator.isManagingAssignment())
                        .selfAssignment(collaborator.isSelfAssignment())
                        .active(collaborator.isActive())
                        .joinedAt(collaborator.getJoinedAt())
                        .build();

                users.add(currentProjectUser);
            }
        }

        result.setUsers(users);
        result.setVersion(project.getVersion());

        return result;
    }

    public Project mapJPAEntityToProject(ProjectJPAEntity jpaEntity) {
        List<ProjectUser> projectUsers = jpaEntity.getUsers();
        List<Collaborator> collaborators = new ArrayList<>(10);

        UserId ownerUserId = null;
        for (ProjectUser projectUser : projectUsers) {
            if (projectUser.isOwner()) {
                ownerUserId = new UserId(projectUser.getId().getUserId());
            } else {
                collaborators.add(Collaborator.joinedCollaborator(
                        new UserId(projectUser.getId().getUserId()),
                        new CollaboratorRole(projectUser.getRole()),
                        projectUser.isManagingAssignment(),
                        projectUser.isSelfAssignment(),
                        projectUser.isActive(),
                        projectUser.getJoinedAt()
                ));
            }
        }

        return Project.existingProject(
                new ProjectId(jpaEntity.getId()),
                ownerUserId,
                new ProjectTitle(jpaEntity.getTitle()),
                jpaEntity.getDescription() != null ? new Description(jpaEntity.getDescription()) : null,
                jpaEntity.getStatus(),
                jpaEntity.getRegisteredAt(),
                jpaEntity.getStartedAt(),
                jpaEntity.getClosedAt(),
                jpaEntity.getEditedAt(),
                collaborators.isEmpty() ? null : collaborators,
                jpaEntity.getVersion()
        );
    }
}
