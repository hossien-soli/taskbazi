package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.common.domain.value.GenericUser;
import dev.hspl.taskbazi.user.domain.exception.InvalidAccessTokenException;
import dev.hspl.taskbazi.user.domain.service.AccessTokenService;
import dev.hspl.taskbazi.user.domain.value.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAccessTokenService implements AccessTokenService {

    @Override
    public AccessToken generateTokenForUser(GenericUser genericUser, short accessTokenLifetimeMinutes) {
        return null;
    }

    @Override
    public GenericUser validateTokenAndExtractUserInfo(AccessToken token) throws InvalidAccessTokenException {
        return null;
    }
}
