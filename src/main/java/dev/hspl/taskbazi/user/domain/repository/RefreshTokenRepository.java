package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    short numberOfUserActiveLoginSessions(
            UserId userId,
            UserRole userRole
    ); // role is needed when we have separate tables for each role's login sessions

    void save(
            RefreshToken refreshToken,
            UserRole userRole
    ); // role is needed when we have separate tables for each role's login sessions

    Optional<RefreshToken> find(
            UUID refreshTokenId,
            UserRole userRole
    );
}
