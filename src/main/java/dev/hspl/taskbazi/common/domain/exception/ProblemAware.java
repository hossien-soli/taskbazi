package dev.hspl.taskbazi.common.domain.exception;

public interface ProblemAware {
    String problemKey();
    // a message key for the user (user-friendly message)

    short groupingValue(); // always enter related http status code for simplicity to exception handling
}
