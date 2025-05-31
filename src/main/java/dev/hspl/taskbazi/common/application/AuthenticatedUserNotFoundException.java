package dev.hspl.taskbazi.common.application;

public class AuthenticatedUserNotFoundException extends ApplicationException {
    public AuthenticatedUserNotFoundException() {
        super("can't find any authenticated user!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
