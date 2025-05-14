package dev.hspl.taskbazi.common.infrastructure.message.notification.handler;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.Set;

// because we have email outboxing and push-notification(native-app) outboxing it is better to...
// ...call handler inside an active database transaction using OutboxingAwareNotificationRequestHandler
// otherwise never call these heavy operations(sending email or push-notification) inside an active database transaction
// in fact this implementation sends PersistedNotification directly(if we have OutboxingGlobalEmailSender and push-notification outboxing)
// direct = It means the delivery logic executes/calls directly within the current active transaction, rather than asynchronously (async) or through any other execution method
// OutboxingAware? = knows that email sender is outboxed and native-app push-notification sender is also outboxed

@Component
@RequiredArgsConstructor
public class OutboxingAwareNotificationRequestHandler implements NotificationRequestHandler {
    private final List<NotificationDeliveryAgent> deliveryAgents;
    private final MessageSource messageSource; // Necessary for constructing the notification message content
    private final TemplateEngine htmlTemplateEngine; // Necessary for constructing the notification message content

    @Override
    @Transactional // because all delivery agents are working with database!!
    public void handle(NotificationRequest request) {
        NotificationRecipient recipient = request.recipient();
        UserFriendlyMessage message = request.prepareMessage(messageSource,htmlTemplateEngine);
        Set<NotificationDeliveryMethod> deliveryMethods = request.deliveryMethods();

        for (NotificationDeliveryMethod method : deliveryMethods) {
            for (NotificationDeliveryAgent currentAgent : this.deliveryAgents) {
                boolean support = currentAgent.support(method);
                if (support) { currentAgent.tryDeliver(message,recipient); }
            }
        }
    }
}
