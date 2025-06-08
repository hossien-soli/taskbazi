package dev.hspl.taskbazi.notification;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;

// implementations: DefaultEmailSenderAPI, NotificationServiceEmailSenderAPI(communicate to notification microservice through http)
// migration to microservices: keep this file and:
// keep this file and replace the implementation with an http based sender API that calls an email delivery rest-api microservice
// usually notification microservice
// and no need for any modification to higher level classes of application(Dependency inversion)
// in fact you need to move any other file of this package to a dedicated spring-boot app and only keep this file...
// ...for direct email sending call to the notification microservice
// also consider persistence and database for migration to microservices

public interface EmailSenderAPI {
    void send(EmailAddress targetEmailAddress, UserFriendlyMessage message);
}
