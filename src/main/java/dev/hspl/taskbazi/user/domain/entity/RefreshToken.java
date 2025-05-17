package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.DomainException;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.event.RefreshTokenReuseDetectedAlertEvent;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.event.NewAccountLoginDomainEvent;
import dev.hspl.taskbazi.user.domain.exception.ActualRefreshTokenMismatchException;
import dev.hspl.taskbazi.user.domain.exception.ClosedLoginSessionException;
import dev.hspl.taskbazi.user.domain.service.OpaqueTokenProtector;
import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

// session = login-session | sessionState = loginSessionState
// or in technical and outside the domain -> login-session = refresh-token-family
// In the database, login sessions will be persisted in a separate table
// we should have a scheduled task for deleting old finished tokens & sessions(IMPORTANT)

@Getter
public class RefreshToken extends DomainAggregateRoot {
    private final UUID id;

    private final ProtectedOpaqueToken actualToken;

    private short lifetimeHours; // token expiration = login-session expiration
    private boolean refreshed;
    private final LocalDateTime createdAt;
    private LocalDateTime refreshedAt; // default=NULL

    private final LoginSession loginSession; // refresh token family

    private final Short version; // just a data transfer property

    private RefreshToken(
            UUID id,
            ProtectedOpaqueToken actualToken,
            short lifetimeHours,
            boolean refreshed,
            LocalDateTime createdAt,
            LocalDateTime refreshedAt,
            LoginSession loginSession,
            Short version
    ) {
        this.id = id;
        this.actualToken = actualToken;
        this.lifetimeHours = lifetimeHours;
        this.refreshed = refreshed;
        this.createdAt = createdAt;
        this.refreshedAt = refreshedAt;
        this.loginSession = loginSession;
        this.version = version;
    }

    public static RefreshToken newLogin(
            LocalDateTime currentDateTime,
            UUID newRefreshTokenId,
            PlainOpaqueToken plainActualRefreshToken,
            UUID newLoginSessionId,
            UniversalUser userToLogin,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            UserAuthenticationConstraints constraints,
            OpaqueTokenProtector tokenProtector
    ) {
        ProtectedOpaqueToken protectedRefreshToken = tokenProtector.protect(plainActualRefreshToken);
        short tokenLifetime = constraints.refreshTokenLifetimeHours();

        LoginSession loginSession = LoginSession.newSession(currentDateTime,newLoginSessionId,
                userToLogin.universalUserId(),requestClientIdentifier,requestIdentificationDetails);

        DomainNotificationRequestEvent notifRequestEvent = new NewAccountLoginDomainEvent(
                currentDateTime,
                userToLogin.userRole(),
                userToLogin.universalUserId(),
                userToLogin.universalUserEmailAddress(),
                requestClientIdentifier,
                requestIdentificationDetails,
                newLoginSessionId
        );

        RefreshToken result = new RefreshToken(newRefreshTokenId,protectedRefreshToken,tokenLifetime,false,
                currentDateTime,null,loginSession,null);

        result.registerDomainEvent(notifRequestEvent);

        return result;
    }

    public static RefreshToken newRotate(
            LocalDateTime currentDateTime,
            UUID newRefreshTokenId,
            PlainOpaqueToken newPlainActualRefreshToken,
            LoginSession relatedLoginSession, // the login session of previous refresh token
            UserAuthenticationConstraints constraints,
            OpaqueTokenProtector tokenProtector
    ) {
        ProtectedOpaqueToken protectedRefreshToken = tokenProtector.protect(newPlainActualRefreshToken);

        return new RefreshToken(newRefreshTokenId,protectedRefreshToken,constraints.refreshTokenLifetimeHours(),
                false,currentDateTime,null,relatedLoginSession,null);
    }

    public static RefreshToken existingInstance(
            UUID id,
            ProtectedOpaqueToken actualToken,
            short lifetimeHours,
            boolean refreshed,
            LocalDateTime createdAt,
            LocalDateTime refreshedAt,
            Short version,
            UUID loginSessionId,
            UserId userId,
            int sessionNumberOfTokenRefresh, // login session
            LoginSessionState sessionState,  // login session
            RequestClientIdentifier requestClientIdentifier, // login session
            RequestIdentificationDetails requestIdentificationDetails, // login session
            LocalDateTime sessionCreatedAt, // login session
            LocalDateTime sessionStateUpdatedAt, // login session
            Integer sessionVersion
    ) {
        LoginSession loginSession = LoginSession.existingSession(
                loginSessionId,userId,sessionNumberOfTokenRefresh,sessionState,requestClientIdentifier,
                requestIdentificationDetails,sessionCreatedAt,sessionStateUpdatedAt,sessionVersion
        );

        return new RefreshToken(id,actualToken,lifetimeHours,refreshed,createdAt,
                refreshedAt,loginSession,version);
    }

    public TokenRefreshResult tryRefresh(
            LocalDateTime currentDateTime,
            PlainOpaqueToken userPlainActualRefreshToken,
            UniversalUser relatedUser,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            OpaqueTokenProtector tokenProtector
    ) throws DomainException {
        boolean tokenMatches = tokenProtector.matches(userPlainActualRefreshToken,this.actualToken);
        if (!tokenMatches) {
            throw new ActualRefreshTokenMismatchException();
        }

        if (this.loginSession.isClosed()) {
            throw new ClosedLoginSessionException();
        }

        if (this.refreshed) {
            this.loginSession.updateState(currentDateTime,LoginSessionState.INVALIDATED);

            DomainNotificationRequestEvent reuseAlertEvent = new RefreshTokenReuseDetectedAlertEvent(
                    currentDateTime,
                    this.id,
                    relatedUser.userRole(),
                    relatedUser.universalUserId(),
                    requestClientIdentifier,
                    requestIdentificationDetails,
                    relatedUser.universalUserEmailAddress()
            );

            registerDomainEvent(reuseAlertEvent);

            return TokenRefreshResult.REUSE_DETECTION;
        }

        long hoursElapsed = Math.abs(Duration.between(currentDateTime,this.createdAt).toHours());
        if (hoursElapsed >= this.lifetimeHours) {
            this.loginSession.updateState(currentDateTime,LoginSessionState.EXPIRED);
            return TokenRefreshResult.EXPIRED;
        }

        this.refreshed = true;
        this.refreshedAt = currentDateTime;
        this.loginSession.newTokenRefresh(requestClientIdentifier,requestIdentificationDetails);
        return TokenRefreshResult.SUCCESS;
    }
}
