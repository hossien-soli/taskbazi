package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.exception.MissingDeliveryAgentTargetIdentifierException;

public interface NotificationDeliveryAgent {
    boolean support(NotificationDeliveryMethod deliveryMethod);

    void tryDeliver(
            UserFriendlyMessage message,
            NotificationRecipient recipient
    ) throws MissingDeliveryAgentTargetIdentifierException;
}
