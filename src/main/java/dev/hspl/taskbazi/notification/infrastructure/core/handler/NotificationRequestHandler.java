package dev.hspl.taskbazi.notification.infrastructure.core.handler;

import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;

// OutboxingAwareNotificationRequestHandler -> ...
// because we have email outboxing and push-notification(native-app) outboxing it is better to...
// ...call handler inside an active database transaction using OutboxingAwareNotificationRequestHandler
// otherwise never call these heavy operations(sending email or push-notification) inside an active database transaction
// OutboxingAware? = knows that email sender is outboxed and native-app push-notification sender is also outboxed

// other implementations -> DirectNotificationRequestHandler, AsyncNotificationRequestHandler,
// OutboxingNotificationRequestHandler
// direct = It means the delivery logic executes/calls directly within the current active transaction, rather than asynchronously (async) or through any other execution method
// outboxing??? -> it just stores NotificationRequest inside database and actual delivery occurs later in scheduled task
// note that the implementation of GlobalEmailSender also is outboxing

public interface NotificationRequestHandler {
    // returns the count of success hits
    byte handle(NotificationRequest request);
}
