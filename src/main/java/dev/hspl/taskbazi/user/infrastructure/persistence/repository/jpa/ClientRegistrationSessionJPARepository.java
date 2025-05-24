package dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.user.infrastructure.persistence.entity.ClientRegistrationSessionJPAEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClientRegistrationSessionJPARepository extends JpaRepository<ClientRegistrationSessionJPAEntity, UUID> {
    @Query("""
            SELECT crs.createdAt FROM ClientRegistrationSession crs \
            WHERE crs.emailAddress = :emailAddress OR crs.requestClientIdentifier = :requestClientIdentifier \
            ORDER BY crs.createdAt DESC""")
    List<LocalDateTime> creationTimeOfLastSessionsByEmailOrRequestClientID(
            @Param("emailAddress") String emailAddress,
            @Param("requestClientIdentifier") String requestClientIdentifier,
            Limit limit
    );
}
