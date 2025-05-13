package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.GenericUser;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.exception.PasswordMismatchException;
import dev.hspl.taskbazi.user.domain.exception.TooManyActiveLoginSessionException;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class UserLoginService {
    private final PasswordProtector passwordProtector;
    private final UserAuthenticationConstraints constraints;
    private final OpaqueTokenProtector tokenProtector;
    private final AccessTokenService accessTokenService;

    public LoginSessionTrackingInfo loginUser(
            LocalDateTime currentDateTime,
            AuthenticatableUser authenticatableUser,
            GenericUser genericUserInfo,
            PlainPassword plainPassword,
            int numberOfUserActiveSessions,
            UUID newRefreshTokenId,
            UUID newLoginSessionId,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            PlainOpaqueToken plainRefreshToken
    ) {
        ProtectedPassword userPassword = authenticatableUser.getAuthUserProtectedPassword();
        boolean matches = passwordProtector.matches(plainPassword,userPassword);
        if (!matches) { throw new PasswordMismatchException(); }

        short maxAllowedActiveLoginSessionsByUser = constraints.maxAllowedActiveLoginSessions();
        boolean canHaveMoreSession = numberOfUserActiveSessions < maxAllowedActiveLoginSessionsByUser;
        if (!canHaveMoreSession) {
            throw new TooManyActiveLoginSessionException(maxAllowedActiveLoginSessionsByUser);
        }

        RefreshToken refreshToken = RefreshToken.newLogin(
                currentDateTime,
                newRefreshTokenId,
                plainRefreshToken,
                newLoginSessionId,
                genericUserInfo,
                requestClientIdentifier,
                requestIdentificationDetails,
                constraints,
                tokenProtector
        );

        AccessToken accessToken = accessTokenService.generateTokenForUser(
                genericUserInfo,
                constraints.accessTokenLifetimeMinutes()
        );

        return new LoginSessionTrackingInfo(accessToken, refreshToken);
    }
}
