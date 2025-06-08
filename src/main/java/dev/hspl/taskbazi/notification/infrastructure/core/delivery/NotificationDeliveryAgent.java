package dev.hspl.taskbazi.notification.infrastructure.core.delivery;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.exception.MissingDeliveryAgentTargetIdentifierException;

public interface NotificationDeliveryAgent {
    boolean support(NotificationDeliveryMethod deliveryMethod);

    void tryDeliver(
            UserFriendlyMessage message,
            NotificationRecipient recipient
    ) throws MissingDeliveryAgentTargetIdentifierException;
}
