package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class UnacceptableCollaboratorRoleException extends DomainException {
    public UnacceptableCollaboratorRoleException() {
        super("The collaborator role is unacceptable. It must be between 5 and 50 characters long.");
    }

    @Override
    public String problemKey() {
        return "project.collaborator_role.unacceptable";
    }
}
