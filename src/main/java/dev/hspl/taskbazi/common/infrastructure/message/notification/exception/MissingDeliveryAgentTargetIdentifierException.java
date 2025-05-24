package dev.hspl.taskbazi.common.infrastructure.message.notification.exception;

public class MissingDeliveryAgentTargetIdentifierException extends RuntimeException {
    public MissingDeliveryAgentTargetIdentifierException(String message) {
        super(message);
    }
}
