package dev.hspl.taskbazi.notification.infrastructure.core.delivery.impl;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class NativeApplicationNotificationDeliveryAgent implements NotificationDeliveryAgent {
    //private final UserNativeApplicationDetector userNativeApplicationDetector;

    @Override
    public boolean support(NotificationDeliveryMethod deliveryMethod) {
        return deliveryMethod.equals(NotificationDeliveryMethod.NATIVE_APPLICATION);
    }

    @Override
    public void tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        // do nothing and just return for now!!!
        // we should create an outbox pattern for pushing notifications to the native front-end applications
        // OUTBOX-PATTERN
    }
}
