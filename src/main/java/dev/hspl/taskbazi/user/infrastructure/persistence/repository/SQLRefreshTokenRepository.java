package dev.hspl.taskbazi.user.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.repository.RefreshTokenRepository;
import dev.hspl.taskbazi.user.domain.value.LoginSessionState;
import dev.hspl.taskbazi.user.infrastructure.persistence.UserModulePersistenceMapper;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.LoginSessionJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.RefreshTokenJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.LoginSessionJPARepository;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.RefreshTokenJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SQLRefreshTokenRepository implements RefreshTokenRepository {
    private final RefreshTokenJPARepository tokenJPARepository;
    private final LoginSessionJPARepository sessionJPARepository;
    private final UserModulePersistenceMapper mapper;

    private short numberOfUserActiveLoginSessions(UserId userId) {
        return sessionJPARepository.countByUserIdAndState(userId.value(),LoginSessionState.ACTIVE);
    }

    @Override
    public short numberOfClientActiveLoginSessions(UserId clientId) {
        return numberOfUserActiveLoginSessions(clientId);
    }

    private void saveForUser(RefreshToken refreshToken) {
        RefreshTokenJPAEntity tokenJPAEntity = mapper.mapRefreshTokenToJPAEntity(refreshToken);
        LoginSessionJPAEntity sessionJPAEntity = tokenJPAEntity.getLoginSession();

        sessionJPARepository.save(sessionJPAEntity);
        tokenJPARepository.save(tokenJPAEntity);
    }

    @Override
    public void saveForClient(RefreshToken refreshToken) {
        saveForUser(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findForClient(UUID refreshTokenId) {

        return null;
    }
}
