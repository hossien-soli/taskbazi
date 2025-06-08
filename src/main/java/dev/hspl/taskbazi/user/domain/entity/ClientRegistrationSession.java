package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.exception.DomainException;
import dev.hspl.taskbazi.common.domain.exception.RequestClientIdentifierMismatchException;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.exception.ClosedRegistrationSessionException;
import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import dev.hspl.taskbazi.user.domain.service.VerificationCodeProtector;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

// only for non-auth2-oidc users
// we should have a scheduled task to remove old garbage registration sessions

@Getter
public class ClientRegistrationSession extends DomainAggregateRoot {
    private final UUID id;

    private final EmailAddress emailAddress;

    private final UserFullName clientFullName;
    private final Username clientUsername;
    private final ProtectedPassword clientPassword;

    private final ProtectedVerificationCode verificationCode;
    private final RequestClientIdentifier requestClientIdentifier; // usually is an ip address
    // be careful with reverse proxies like nginx(same ip comes to application)
    // technical client (server/client) not a business client

    private short verificationAttempts;

    private boolean blocked; // too many attempts!!!
    private boolean verified;
    private boolean registered;
    private boolean expired;

    private final LocalDateTime createdAt;
    private LocalDateTime closedAt;  // closed = blocked or registered or expired

    private final Short version; // default=NULL - just a data transfer property - no domain level concurrency control CHECK!!

    private ClientRegistrationSession(
            UUID id,
            EmailAddress emailAddress,
            UserFullName clientFullName,
            Username clientUsername,
            ProtectedPassword userPassword,
            ProtectedVerificationCode verificationCode,
            RequestClientIdentifier requestClientIdentifier,
            short verificationAttempts,
            boolean blocked,
            boolean verified,
            boolean registered,
            boolean expired,
            LocalDateTime createdAt,
            LocalDateTime closedAt,
            Short version
    ) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.clientFullName = clientFullName;
        this.clientUsername = clientUsername;
        this.clientPassword = userPassword;
        this.verificationCode = verificationCode;
        this.requestClientIdentifier = requestClientIdentifier;
        this.verificationAttempts = verificationAttempts;
        this.blocked = blocked;
        this.verified = verified;
        this.registered = registered;
        this.expired = expired;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.version = version;
    }

    public static ClientRegistrationSession newSession(
            LocalDateTime currentDateTime,
            UUID newSessionId,
            EmailAddress emailAddress,
            UserFullName clientFullName,
            Username clientUsername,
            ProtectedPassword clientPassword,
            PlainVerificationCode plainVerificationCode,
            RequestClientIdentifier requestClientIdentifier,
            VerificationCodeProtector verificationCodeProtector
    ) {
        ProtectedVerificationCode verificationCode = verificationCodeProtector.protect(plainVerificationCode);

        return new ClientRegistrationSession(newSessionId, emailAddress, clientFullName, clientUsername, clientPassword,
                verificationCode, requestClientIdentifier, (short) 0, false, false, false, false, currentDateTime, null, null);
    }

    public static ClientRegistrationSession existingSession(
            UUID id,
            EmailAddress emailAddress,
            UserFullName clientFullName,
            Username clientUsername,
            ProtectedPassword clientPassword,
            ProtectedVerificationCode verificationCode,
            RequestClientIdentifier requestClientIdentifier,
            short verificationAttempt,
            boolean blocked,
            boolean verified,
            boolean registered,
            boolean expired,
            LocalDateTime createdAt,
            LocalDateTime closedAt,
            Short version
    ) {
        return new ClientRegistrationSession(id, emailAddress, clientFullName, clientUsername, clientPassword,
                verificationCode, requestClientIdentifier, verificationAttempt, blocked, verified, registered, expired,
                createdAt, closedAt, version);
    }

    public boolean isClosed() {
        return blocked || registered || expired;
    }

    public void registrationCompleted(LocalDateTime registrationDateTime) {
        this.registered = true;
        this.closedAt = registrationDateTime;
    }

    public RegistrationVerificationResult tryVerify(
            LocalDateTime currentDateTime,
            PlainVerificationCode userVerificationCode,
            RequestClientIdentifier userRequestClientIdentifier,
            VerificationCodeProtector verificationCodeProtector,
            UserAuthenticationConstraints constraints
    ) throws DomainException {
        if (isClosed()) {
            throw new ClosedRegistrationSessionException();
        }

        boolean sameClient = this.requestClientIdentifier.equals(userRequestClientIdentifier);
        if (!sameClient) {
            throw new RequestClientIdentifierMismatchException();
        }

        boolean canVerify = this.verificationAttempts < constraints.registrationSessionMaxAllowedAttempts();
        if (!canVerify) {
            this.blocked = true;
            this.closedAt = currentDateTime;
            return RegistrationVerificationResult.TOO_MANY_ATTEMPTS;
        }

        int secondsElapsed = (int) Math.abs(Duration.between(currentDateTime, this.createdAt).toSeconds());
        boolean expired = secondsElapsed >= constraints.registrationSessionLifetimeSeconds();
        if (expired) {
            this.expired = true;
            this.closedAt = currentDateTime;
            return RegistrationVerificationResult.EXPIRED;
        }

        if (this.verified) {
            return RegistrationVerificationResult.SUCCESS;
        }

        this.verificationAttempts++;

        boolean matches = verificationCodeProtector.matches(userVerificationCode, this.verificationCode);
        if (!matches) {
            return RegistrationVerificationResult.CODE_MISMATCH;
        }

        this.verified = true;
        return RegistrationVerificationResult.SUCCESS;
    }
}
