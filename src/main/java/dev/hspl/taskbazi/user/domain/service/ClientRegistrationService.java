package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.entity.Client;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.exception.BadSessionStateRegistrationException;
import dev.hspl.taskbazi.user.domain.exception.EmailAddressAlreadyInUseException;
import dev.hspl.taskbazi.user.domain.exception.RegistrationSessionRestrictionException;
import dev.hspl.taskbazi.user.domain.exception.UsernameAlreadyInUseException;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ClientRegistrationService {
    private final PasswordProtector passwordProtector;

    @Getter
    private final VerificationCodeProtector verificationCodeProtector;

    private final UserUniquenessChecker userUniquenessChecker;
    private final ClientRegistrationEmailSender emailSender;

    @Getter
    private final UserAuthenticationConstraints constraints;

    public ClientRegistrationSession createNewSession(
            LocalDateTime currentDateTime,
            UUID newSessionId,
            EmailAddress emailAddress,
            ClientFullName userFullName,
            Username userUsername,
            PlainPassword userPlainPassword,
            PlainVerificationCode plainVerificationCode,
            RequestClientIdentifier requestClientIdentifier,
            LocalDateTime lastSessionCreationTime // last session creation by email address or request client identifier
    ) {
//        boolean isUsernameUnique = userUniquenessChecker.checkUsernameIsUnique(userUsername);
//        if (!isUsernameUnique) { throw new UsernameAlreadyInUseException(); }
//
//        boolean isEmailAddressUnique = userUniquenessChecker.checkEmailAddressIsUnique(emailAddress);
//        if (!isEmailAddressUnique) { throw new EmailAddressAlreadyInUseException(); }

        int sessionLimitationDelay = constraints.registrationSessionLimitationDelaySeconds();

        boolean hasPreviousSession = lastSessionCreationTime != null; // regardless of the last session state
        if (hasPreviousSession) {
            int secondsElapsed = (int) Math.abs(Duration.between(lastSessionCreationTime,currentDateTime).toSeconds());
            boolean canCreateNewSession = secondsElapsed >= sessionLimitationDelay;
            if (!canCreateNewSession) {
                throw new RegistrationSessionRestrictionException(
                        sessionLimitationDelay,
                        sessionLimitationDelay - secondsElapsed
                );
            }
        }

        ProtectedPassword userPassword = passwordProtector.protect(userPlainPassword);

        int sessionLifetime = constraints.registrationSessionLifetimeSeconds();
        emailSender.sendVerificationEmail(
                emailAddress,
                plainVerificationCode,
                sessionLifetime,
                currentDateTime.plusSeconds(sessionLifetime),
                userFullName
        );

        return ClientRegistrationSession.newSession(currentDateTime,newSessionId,emailAddress,userFullName,userUsername,
                userPassword,plainVerificationCode,requestClientIdentifier,verificationCodeProtector);
    }

    public Client registerClient(
            LocalDateTime currentDateTime,
            UserId newUserId,
            ClientRegistrationSession registrationSession
    ) {
        boolean validate = !registrationSession.isClosed() && registrationSession.isVerified();
        if (!validate) { throw new BadSessionStateRegistrationException(); }

        registrationSession.registrationCompleted(currentDateTime);

        ClientFullName userFullName = registrationSession.getUserFullName();
        EmailAddress userEmailAddress = registrationSession.getEmailAddress();
        Username userUsername = registrationSession.getUserUsername();
        ProtectedPassword userPassword = registrationSession.getUserPassword();

        boolean isUsernameUnique = userUniquenessChecker.checkUsernameIsUnique(userUsername);
        if (!isUsernameUnique) { throw new UsernameAlreadyInUseException(); }

        boolean isEmailAddressUnique = userUniquenessChecker.checkEmailAddressIsUnique(userEmailAddress);
        if (!isEmailAddressUnique) { throw new EmailAddressAlreadyInUseException(); }

        emailSender.sendWelcomeEmail(userEmailAddress,userUsername,userFullName,currentDateTime);
        // instead of this send a welcome notification to the user using domain events and AbstractAggregateRoot

        return Client.newUniqueClient(currentDateTime,newUserId,userFullName,userEmailAddress,userUsername,userPassword);
    }

    //    public void changeUserUsername(
//            Client user,
//            Username newUsername
//    ) throws UsernameAlreadyInUseException {
//        Username currentUsername = user.getUsername();
//        boolean ignore = currentUsername.equals(newUsername);
//        if (ignore) return;
//
//        boolean isUsernameUnique = uniquenessChecker.checkUsername(newUsername);
//        if (!isUsernameUnique) {
//            throw new UsernameAlreadyInUseException();
//        }
//
//        user.updateUsername(newUsername);
//    }

//    public void changeUserEmail(
//            Client user,
//            EmailAddress newEmailAddress
//    ) throws EmailAddressAlreadyInUseException {
//        EmailAddress currentEmail = user.getEmailAddress();
//        boolean ignore = currentEmail.equals(newEmailAddress);
//        if (ignore) return;
//
//        boolean isEmailUnique = uniquenessChecker.checkEmail(newEmailAddress);
//        if (!isEmailUnique) {
//            throw new EmailAddressAlreadyInUseException();
//        }
//
//        user.updateEmailAddress(newEmailAddress);
//    }

}
