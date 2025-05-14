package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.value.GenericUser;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.event.NewAccountLoginDomainEvent;
import dev.hspl.taskbazi.user.domain.service.OpaqueTokenProtector;
import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import dev.hspl.taskbazi.user.domain.value.LoginSessionState;
import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;
import dev.hspl.taskbazi.user.domain.value.ProtectedOpaqueToken;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

// session = login-session | sessionState = loginSessionState
// or in technical and outside the domain -> login-session = refresh-token-family
// In the database, login sessions will be persisted in a separate table

@Getter
public class RefreshToken extends DomainAggregateRoot {
    private final UUID id;

    private final ProtectedOpaqueToken actualToken;

    private final short lifetimeHours; // token expiration = login-session expiration
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
            GenericUser genericUserInfo,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            UserAuthenticationConstraints constraints,
            OpaqueTokenProtector tokenProtector
    ) {
        ProtectedOpaqueToken protectedRefreshToken = tokenProtector.protect(plainActualRefreshToken);
        short tokenLifetime = constraints.refreshTokenLifetimeHours();

        LoginSession loginSession = LoginSession.newSession(currentDateTime,newLoginSessionId,
                genericUserInfo.genericUserId(),requestClientIdentifier,requestIdentificationDetails);

        DomainNotificationRequestEvent notifRequestEvent = new NewAccountLoginDomainEvent(
                currentDateTime,
                RefreshToken.class.getSimpleName(),
                newRefreshTokenId,
                genericUserInfo.userRole(),
                genericUserInfo.genericUserId(),
                genericUserInfo.genericUserEmailAddress(),
                requestClientIdentifier,
                requestIdentificationDetails,
                newLoginSessionId
        );

        RefreshToken result = new RefreshToken(newRefreshTokenId,protectedRefreshToken,tokenLifetime,false,
                currentDateTime,null,loginSession,null);

        result.registerDomainEvent(notifRequestEvent);

        return result;
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

    public void markAsRefreshed(
            LocalDateTime currentDateTime,
            RequestClientIdentifier newRequestClientIdentifier,
            RequestIdentificationDetails newRequestIdentificationDetails
    ) {
        this.refreshed = true;
        this.refreshedAt = currentDateTime;
        this.loginSession.newTokenRefresh(newRequestClientIdentifier,newRequestIdentificationDetails);
        //registerDomainEvent(new TokenRefreshedDomainEvent());
    }
}
