package dev.hspl.taskbazi.common.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.infrastructure.persistence.entity.PersistedNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersistedNotificationJPARepository extends JpaRepository<PersistedNotification, UUID> {

}
