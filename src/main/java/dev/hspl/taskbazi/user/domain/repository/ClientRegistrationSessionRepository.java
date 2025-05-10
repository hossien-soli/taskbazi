package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ClientRegistrationSessionRepository {
    @Nullable
    LocalDateTime getLastSessionByEmailOrRequestClientIdentifier(
            EmailAddress emailAddress,
            RequestClientIdentifier requestClientIdentifier
    ); // null = no-session

    void saveSession(ClientRegistrationSession session);

    Optional<ClientRegistrationSession> findSessionById(UUID sessionId);
}
