package dev.hspl.taskbazi.user.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.repository.ClientRegistrationSessionRepository;
import dev.hspl.taskbazi.user.infrastructure.persistence.UserModulePersistenceMapper;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.ClientRegistrationSessionJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.ClientRegistrationSessionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SQLClientRegistrationSessionRepository implements ClientRegistrationSessionRepository {
    private final ClientRegistrationSessionJPARepository jpaRepository;
    private final UserModulePersistenceMapper mapper;

    @Override
    @Nullable
    public LocalDateTime getLastSessionByEmailOrRequestClientIdentifier(
            EmailAddress emailAddress,
            RequestClientIdentifier requestClientIdentifier
    ) {
        List<LocalDateTime> fetchResult = jpaRepository.creationTimeOfLastSessionsByEmailOrRequestClientID(
                emailAddress.value(),
                requestClientIdentifier.value(),
                Limit.of(1)
        );

        return fetchResult.stream().findFirst().orElse(null);
    }

    @Override
    public void saveSession(ClientRegistrationSession session) {
        ClientRegistrationSessionJPAEntity sessionRecord = mapper.mapClientRegistrationSessionToJPAEntity(session);
        jpaRepository.save(sessionRecord);
    }

    @Override
    public Optional<ClientRegistrationSession> findSessionById(UUID sessionId) {
        Optional<ClientRegistrationSessionJPAEntity> fetchResult = jpaRepository.findById(sessionId);
        return fetchResult.map(mapper::mapJPAEntityToClientRegistrationSession);
    }
}
