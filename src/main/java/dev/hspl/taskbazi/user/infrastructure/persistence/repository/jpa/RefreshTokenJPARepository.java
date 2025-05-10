package dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.user.infrastructure.persistence.entity.RefreshTokenJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenJPARepository extends JpaRepository<RefreshTokenJPAEntity, UUID> {

}
