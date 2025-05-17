package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.domain.value.LoginSessionState;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

// login session = refresh token family
// expiration of the login session is based on the last refresh-token expiration
// requestIdentificationDetails = ip lookup info and user-agent extracted info for http based clients
// request-client-identifier and request-identification-details must be updated after each token refresh

@Getter
public class LoginSession {
    private final UUID id;
    private final UserId userId;

    private int numberOfTokenRefresh;
    private LoginSessionState state;

    private RequestClientIdentifier requestClientIdentifier; // not-null!!!!
    private RequestIdentificationDetails requestIdentificationDetails; // nullable

    private final LocalDateTime createdAt;
    private LocalDateTime stateUpdatedAt; // default=NULL

    private final Integer version; // just a data transfer property

    private LoginSession(
            UUID id,
            UserId userId,
            int numberOfTokenRefresh,
            LoginSessionState state,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            LocalDateTime createdAt,
            LocalDateTime stateUpdatedAt,
            Integer version
    ) {
        this.id = id;
        this.userId = userId;
        this.numberOfTokenRefresh = numberOfTokenRefresh;
        this.state = state;
        this.requestClientIdentifier = requestClientIdentifier;
        this.requestIdentificationDetails = requestIdentificationDetails;
        this.createdAt = createdAt;
        this.stateUpdatedAt = stateUpdatedAt;
        this.version = version;
    }

    public static LoginSession newSession(
            LocalDateTime currentDateTime,
            UUID newSessionId,
            UserId userId,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails
    ) {
        return new LoginSession(newSessionId,userId,0,LoginSessionState.ACTIVE,requestClientIdentifier,
                requestIdentificationDetails,currentDateTime,null,null);
    }

    public static LoginSession existingSession(
            UUID id,
            UserId userId,
            int numberOfTokenRefresh,
            LoginSessionState state,
            RequestClientIdentifier requestClientIdentifier,
            RequestIdentificationDetails requestIdentificationDetails,
            LocalDateTime createdAt,
            LocalDateTime stateUpdatedAt,
            Integer version
    ) {
        return new LoginSession(id,userId,numberOfTokenRefresh,state,requestClientIdentifier,
                requestIdentificationDetails,createdAt,stateUpdatedAt,version);
    }

    public boolean isClosed() {
        return !this.state.equals(LoginSessionState.ACTIVE);
    }

    public void newTokenRefresh(
            RequestClientIdentifier newRequestClientIdentifier,
            RequestIdentificationDetails newRequestIdentificationDetails
    ) {
        this.numberOfTokenRefresh++;
        this.requestClientIdentifier = newRequestClientIdentifier;
        this.requestIdentificationDetails = newRequestIdentificationDetails;
    }

    public void updateState(LocalDateTime currentDateTime, LoginSessionState newState) {
        this.stateUpdatedAt = currentDateTime;
        this.state = newState;
    }
}
