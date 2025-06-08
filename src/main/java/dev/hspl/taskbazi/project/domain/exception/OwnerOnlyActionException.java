package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class OwnerOnlyActionException extends DomainException {
    public OwnerOnlyActionException() {
        super("only the owner of project can perform this action!!!");
    }

    @Override
    public String problemKey() {
        return "project.modification.owner_only";
    }
}
