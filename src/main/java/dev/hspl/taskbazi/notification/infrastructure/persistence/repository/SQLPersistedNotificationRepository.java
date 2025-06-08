package dev.hspl.taskbazi.notification.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.notification.application.PersistedNotificationRepository;
import dev.hspl.taskbazi.notification.application.model.PersistedNotification;
import dev.hspl.taskbazi.notification.infrastructure.persistence.entity.PersistedNotificationJPAEntity;
import dev.hspl.taskbazi.notification.infrastructure.persistence.repository.jpa.PersistedNotificationJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// replace with a mongodb implementation if you want to switch the application's database to mongodb

@Repository
@RequiredArgsConstructor
public class SQLPersistedNotificationRepository implements PersistedNotificationRepository {
    private final PersistedNotificationJPARepository jpaRepository;

    @Override
    public void saveNewNotification(PersistedNotification notification, UserRole userRole) {
        // ignore user-role since we have all types of users stored in a single database table(user-id is sufficient)
        PersistedNotificationJPAEntity jpaEntity = PersistedNotificationJPAEntity.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .htmlMessage(notification.getHtmlMessage())
                .importantNotification(notification.isImportantNotification())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .isNew(true)
                .build();

        jpaRepository.save(jpaEntity);
    }
}
