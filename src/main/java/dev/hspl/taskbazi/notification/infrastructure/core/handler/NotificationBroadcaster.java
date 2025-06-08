package dev.hspl.taskbazi.notification.infrastructure.core.handler;

import dev.hspl.taskbazi.notification.infrastructure.core.NotificationBroadcastRequest;

// potential implementations:
//    OutboxingAwareNotificationBroadcaster -> act like OutboxingAwareNotificationRequestHandler for each recipient
//    OutboxingNotificationBroadcaster -> first store broadcast requests inside a db table

public interface NotificationBroadcaster {
    void broadcast(NotificationBroadcastRequest broadcastRequest);
}
