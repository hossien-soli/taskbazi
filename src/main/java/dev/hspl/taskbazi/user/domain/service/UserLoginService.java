package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.RequestIdentificationDetails;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.exception.PasswordMismatchException;
import dev.hspl.taskbazi.user.domain.exception.TooManyActiveLoginSessionException;
import dev.hspl.taskbazi.user.domain.exception.UserRoleMismatchLoginException;
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
            UserRole requestedUserRole, // role requested for the login (role based separated logins)
            User userToLogin,
            PlainPassword plainPassword,
            int numberOfUserActiveSessions,
            UUID newRefreshTokenId,
            UUID newLoginSessionId,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            PlainOpaqueToken newPlainActualRefreshToken
    ) {
        boolean roleMatches = requestedUserRole.equals(userToLogin.userRole());
        if (!roleMatches) {
            throw new UserRoleMismatchLoginException();
        }

        ProtectedPassword userPassword = userToLogin.getPassword();
        boolean matches = passwordProtector.matches(plainPassword, userPassword);
        if (!matches) {
            throw new PasswordMismatchException();
        }

        short maxAllowedActiveLoginSessionsByUser = constraints.maxAllowedActiveLoginSessions();
        boolean canHaveMoreSession = numberOfUserActiveSessions < maxAllowedActiveLoginSessionsByUser;
        if (!canHaveMoreSession) {
            throw new TooManyActiveLoginSessionException(maxAllowedActiveLoginSessionsByUser);
        }

        RefreshToken refreshToken = RefreshToken.newLogin(
                currentDateTime,
                newRefreshTokenId,
                newPlainActualRefreshToken,
                newLoginSessionId,
                userToLogin,
                requestClientIdentifier,
                requestIdentificationDetails,
                constraints,
                tokenProtector
        );

        AccessToken accessToken = accessTokenService.generateTokenForUser(
                userToLogin,
                constraints.accessTokenLifetimeMinutes()
        );

        return new LoginSessionTrackingInfo(accessToken, refreshToken);
    }

    // returns a new refresh token for tracking the login session (token rotation)
    // this should detect the duplicate usage of token(reuse detection)
    // can invalidate the login session due to reuse detection
    // also can expire the login session due to a non-refreshed refresh token expiration
    public LoginSessionExtensionResult refreshAndRotateTheToken(
            LocalDateTime currentDateTime,
            UserRole requestedUserRole, // role requested for the login(refresh) (role based separated logins)
            User relatedUser,
            RefreshToken tokenToRefresh,
            PlainOpaqueToken userPlainActualRefreshToken,
            UUID newRefreshTokenId,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            PlainOpaqueToken newPlainActualRefreshToken
    ) {
        boolean roleMatches = requestedUserRole.equals(relatedUser.getRole());
        if (!roleMatches) {
            throw new UserRoleMismatchLoginException();
        }

        TokenRefreshResult refreshResult = tokenToRefresh.tryRefresh(currentDateTime, userPlainActualRefreshToken,
                relatedUser, requestClientIdentifier, requestIdentificationDetails, constraints, tokenProtector);

        if (refreshResult.equals(TokenRefreshResult.SUCCESS)) {
            RefreshToken newRefreshToken = RefreshToken.newRotate(
                    currentDateTime,
                    newRefreshTokenId,
                    newPlainActualRefreshToken,
                    tokenToRefresh.getLoginSession(),
                    constraints,
                    tokenProtector
            );

            AccessToken newAccessToken = accessTokenService.generateTokenForUser(
                    relatedUser,
                    constraints.accessTokenLifetimeMinutes()
            );

            return new LoginSessionExtensionResult(refreshResult, new LoginSessionTrackingInfo(
                    newAccessToken,
                    newRefreshToken
            ));
        }

        return new LoginSessionExtensionResult(refreshResult, null);
    }
}
