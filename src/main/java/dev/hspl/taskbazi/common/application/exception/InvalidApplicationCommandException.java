package dev.hspl.taskbazi.common.application.exception;

public class InvalidApplicationCommandException extends ApplicationException {
    public InvalidApplicationCommandException(String message) {
        super(message);
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }
}
