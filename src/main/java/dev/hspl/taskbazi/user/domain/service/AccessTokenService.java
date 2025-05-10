package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.GenericUser;
import dev.hspl.taskbazi.user.domain.exception.InvalidAccessTokenException;
import dev.hspl.taskbazi.user.domain.value.AccessToken;

// potential implementations -> JWTAccessTokenService, DatabaseAccessTokenService/StatefulAccessTokenService
// both stateful and stateless tokens can be handled by this abstraction
// generateTokenForUser() -> can be a JWT or an opaque token stored in the database
// validateToken -> For stateful tokens, you can extract the token ID from the token string, fetch the token from the database, ...
// and then validate the hashed value stored in the database.

public interface AccessTokenService {
    AccessToken generateTokenForUser(
            GenericUser genericUser,
            short accessTokenLifetimeMinutes
    );

    GenericUser validateTokenAndExtractUserInfo(AccessToken token) throws InvalidAccessTokenException;
}
