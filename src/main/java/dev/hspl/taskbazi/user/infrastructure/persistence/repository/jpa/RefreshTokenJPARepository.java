package dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.user.infrastructure.persistence.entity.RefreshTokenJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenJPARepository extends JpaRepository<RefreshTokenJPAEntity, UUID> {
    @Query("SELECT r FROM RefreshToken r JOIN FETCH r.loginSession WHERE r.id = :id")
    Optional<RefreshTokenJPAEntity> findByIdWithLoginSession(
            @Param("id") UUID id
    ); // using join-fetch clause or entity graphs to load RefreshToken and LoginSession at once
}
