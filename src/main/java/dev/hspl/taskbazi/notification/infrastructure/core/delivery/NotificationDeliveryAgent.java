package dev.hspl.taskbazi.notification.infrastructure.core.delivery;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.exception.MissingDeliveryAgentTargetIdentifierException;

public interface NotificationDeliveryAgent {
    boolean support(NotificationDeliveryMethod deliveryMethod);

    boolean tryDeliver(
            UserFriendlyMessage message,
            NotificationRecipient recipient
    );
}
