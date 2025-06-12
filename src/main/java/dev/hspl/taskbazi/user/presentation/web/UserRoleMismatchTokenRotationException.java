package dev.hspl.taskbazi.user.presentation.web;

import dev.hspl.taskbazi.common.domain.exception.ProblemAware;

public class UserRoleMismatchTokenRotationException extends RuntimeException implements ProblemAware {
    public UserRoleMismatchTokenRotationException() {
        super("refresh token specified user-role doesn't match the http-endpoint required user-role!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
