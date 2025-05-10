package dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.user.domain.value.LoginSessionState;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.LoginSessionJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginSessionJPARepository extends JpaRepository<LoginSessionJPAEntity, UUID> {
    short countByUserIdAndState(UUID userId, LoginSessionState state);
}
