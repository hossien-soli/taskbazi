package dev.hspl.taskbazi.project.domain.value;

import dev.hspl.taskbazi.project.domain.exception.UnacceptableCollaboratorRoleException;

public record CollaboratorRole(String value) {
    public CollaboratorRole {
        boolean validate = value != null && value.length() >= 5 && value.length() <= 50;
        if (!validate) {
            throw new UnacceptableCollaboratorRoleException();
        }
    }
}
