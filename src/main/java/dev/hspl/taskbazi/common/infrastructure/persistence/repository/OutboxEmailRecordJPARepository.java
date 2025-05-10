package dev.hspl.taskbazi.common.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.infrastructure.persistence.entity.OutboxEmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEmailRecordJPARepository extends JpaRepository<OutboxEmailRecord, UUID> {

}
