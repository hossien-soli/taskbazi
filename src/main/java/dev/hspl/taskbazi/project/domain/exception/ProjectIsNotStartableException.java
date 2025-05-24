package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class ProjectIsNotStartableException extends DomainException {
    public ProjectIsNotStartableException() {
        super("current status of project should be REGISTERED or ARCHIVED for starting it!");
    }

    @Override
    public String problemKey() {
        return ""; // TODO: find a good message for this
    }
}
