package dev.hspl.taskbazi.notification.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.notification.infrastructure.persistence.entity.PersistedNotificationJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersistedNotificationJPARepository extends JpaRepository<PersistedNotificationJPAEntity, UUID> {

}
