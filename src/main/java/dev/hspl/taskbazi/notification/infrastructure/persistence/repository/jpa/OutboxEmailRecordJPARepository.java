package dev.hspl.taskbazi.notification.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.notification.infrastructure.persistence.entity.OutboxEmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEmailRecordJPARepository extends JpaRepository<OutboxEmailRecord, UUID> {

}
