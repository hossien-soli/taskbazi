package dev.hspl.taskbazi.common.presentation.web;

import dev.hspl.taskbazi.common.domain.exception.ProblemAware;

public class UnsupportedAuthenticationSecurityException extends RuntimeException implements ProblemAware {
    public UnsupportedAuthenticationSecurityException() {
        super("the type of principal in the SecurityContext.Authentication object bound to the request thread is unsupported!");
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
