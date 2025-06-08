package dev.hspl.taskbazi.notification.infrastructure.core.exception;

public class MissingDeliveryAgentTargetIdentifierException extends RuntimeException {
    public MissingDeliveryAgentTargetIdentifierException(String message) {
        super(message);
    }
}
