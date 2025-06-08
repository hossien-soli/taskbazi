package dev.hspl.taskbazi.notification.application;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.notification.application.model.PersistedNotification;

public interface PersistedNotificationRepository {
    void saveNewNotification(
            PersistedNotification notification,
            UserRole userRole // used only when we want to separate the notifications of each role in different db tables or something else
    );

    // void updateAsRead (don't load the entire entity for just updating read_at field!)
}
