package dev.hspl.taskbazi.common.infrastructure.message.notification;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;

import java.util.Set;

public interface NotificationRequest {
    NotificationRecipient recipient();

    UserFriendlyMessage message();

    Set<NotificationDeliveryMethod> deliveryMethods();
}
