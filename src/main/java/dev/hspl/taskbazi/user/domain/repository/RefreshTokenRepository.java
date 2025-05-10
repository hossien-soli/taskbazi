package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;

public interface RefreshTokenRepository {
    short numberOfClientActiveLoginSessions(UserId clientId);

    void saveForClient(RefreshToken refreshToken);

    // saveForClient()
    // saveForAdmin()
    // saveForModerators()
}
