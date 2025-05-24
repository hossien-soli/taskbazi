package dev.hspl.taskbazi.common.infrastructure.message.notification.handler;

import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationBroadcastRequest;

// potential implementations:
//    OutboxingAwareNotificationBroadcaster -> act like OutboxingAwareNotificationRequestHandler for each recipient
//    OutboxingNotificationBroadcaster -> first store broadcast requests inside a db table

public interface NotificationBroadcaster {
    void broadcast(NotificationBroadcastRequest broadcastRequest);
}
