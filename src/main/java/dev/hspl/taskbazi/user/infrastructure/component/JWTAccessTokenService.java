package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.user.domain.exception.InvalidAccessTokenException;
import dev.hspl.taskbazi.user.domain.service.AccessTokenService;
import dev.hspl.taskbazi.user.domain.value.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAccessTokenService implements AccessTokenService {

    @Override
    public AccessToken generateTokenForUser(UniversalUser genericUser, short accessTokenLifetimeMinutes) {
        return null;
    }

    @Override
    public UniversalUser validateTokenAndExtractUserInfo(AccessToken token) throws InvalidAccessTokenException {
        return null;
    }
}
