package dev.hspl.taskbazi.common.infrastructure.message.notification.handler;

import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;

// DefaultNotificationRequestHandler -> ...
// because we have email outboxing and push-notification(native-app) outboxing it is better to...
// ...call handler inside an active database transaction using DefaultNotificationRequestHandler
// otherwise never call these heavy operations(sending email or push-notification) inside an active database transaction

// other implementations -> DirectNotificationRequestHandler, AsyncNotificationRequestHandler,
// OutboxingNotificationRequestHandler
// outboxing??? -> it just stores NotificationRequest inside database and actual delivery occurs later in scheduled task
// note that the implementation of GlobalEmailSender also is outboxing

public interface NotificationRequestHandler {
    void handle(NotificationRequest request);
}
