package dev.hspl.taskbazi.common.infrastructure.message.notification.handler;

import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// because we have email outboxing and push-notification(native-app) outboxing it is better to...
// ...call handler inside an active database transaction using DefaultNotificationRequestHandler
// otherwise never call these heavy operations(sending email or push-notification) inside an active database transaction
// in fact this implementation sends PersistedNotification directly(if we have OutboxingGlobalEmailSender and push-notification outboxing)

@Component
@RequiredArgsConstructor
public class DefaultNotificationRequestHandler implements NotificationRequestHandler {
    private final List<NotificationDeliveryAgent> deliveryAgents;

    @Override
    public void handle(NotificationRequest request) {

    }
}
