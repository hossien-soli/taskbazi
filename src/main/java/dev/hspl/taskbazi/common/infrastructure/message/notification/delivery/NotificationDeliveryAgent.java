package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;

public interface NotificationDeliveryAgent {
    boolean support(NotificationDeliveryMethod deliveryMethod);

    boolean tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient); // true=success & false=failure
}
