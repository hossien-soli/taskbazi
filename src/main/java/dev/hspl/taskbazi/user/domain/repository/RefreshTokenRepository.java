package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    short numberOfClientActiveLoginSessions(UserId clientId);

    void saveForClient(RefreshToken refreshToken);

    Optional<RefreshToken> findForClient(UUID refreshTokenId);

    // saveForClient()
    // saveForAdmin()
    // saveForModerators()
}
