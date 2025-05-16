package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.exception.BadSessionStateRegistrationException;
import dev.hspl.taskbazi.user.domain.exception.EmailAddressAlreadyInUseException;
import dev.hspl.taskbazi.user.domain.exception.RegistrationSessionRestrictionException;
import dev.hspl.taskbazi.user.domain.exception.UsernameAlreadyInUseException;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;
import dev.hspl.taskbazi.user.domain.value.UserFullName;
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
            EmailAddress clientEmailAddress,
            UserFullName clientFullName,
            Username clientUsername,
            PlainPassword clientPlainPassword,
            PlainVerificationCode plainVerificationCode,
            RequestClientIdentifier requestClientIdentifier,
            LocalDateTime lastSessionCreationTime // last session creation by email address or request client identifier
    ) {
        boolean isUsernameUnique = userUniquenessChecker.checkUsernameIsUnique(clientUsername, UserRole.CLIENT);
        if (!isUsernameUnique) {
            throw new UsernameAlreadyInUseException();
        }

        boolean isEmailAddressUnique = userUniquenessChecker.checkEmailAddressIsUnique(clientEmailAddress, UserRole.CLIENT);
        if (!isEmailAddressUnique) {
            throw new EmailAddressAlreadyInUseException();
        }

        int sessionLimitationDelay = constraints.registrationSessionLimitationDelaySeconds();

        boolean hasPreviousSession = lastSessionCreationTime != null; // regardless of the last session state
        if (hasPreviousSession) {
            int secondsElapsed = (int) Math.abs(Duration.between(lastSessionCreationTime, currentDateTime).toSeconds());
            boolean canCreateNewSession = secondsElapsed >= sessionLimitationDelay;
            if (!canCreateNewSession) {
                throw new RegistrationSessionRestrictionException(
                        sessionLimitationDelay,
                        sessionLimitationDelay - secondsElapsed
                );
            }
        }

        ProtectedPassword userPassword = passwordProtector.protect(clientPlainPassword);

        int sessionLifetime = constraints.registrationSessionLifetimeSeconds();
        emailSender.sendVerificationEmail(
                clientEmailAddress,
                plainVerificationCode,
                sessionLifetime,
                currentDateTime.plusSeconds(sessionLifetime),
                clientFullName
        );

        return ClientRegistrationSession.newSession(currentDateTime, newSessionId, clientEmailAddress, clientFullName, clientUsername,
                userPassword, plainVerificationCode, requestClientIdentifier, verificationCodeProtector);
    }

    public User registerClient(
            LocalDateTime currentDateTime,
            UserId newUserId,
            ClientRegistrationSession registrationSession
    ) {
        boolean validate = !registrationSession.isClosed() && registrationSession.isVerified();
        if (!validate) {
            throw new BadSessionStateRegistrationException();
        }

        registrationSession.registrationCompleted(currentDateTime);

        UserFullName clientFullName = registrationSession.getClientFullName();
        EmailAddress clientEmailAddress = registrationSession.getEmailAddress();
        Username clientUsername = registrationSession.getClientUsername();
        ProtectedPassword clientPassword = registrationSession.getClientPassword();

        boolean isUsernameUnique = userUniquenessChecker.checkUsernameIsUnique(clientUsername, UserRole.CLIENT);
        if (!isUsernameUnique) {
            throw new UsernameAlreadyInUseException();
        }

        boolean isEmailAddressUnique = userUniquenessChecker.checkEmailAddressIsUnique(clientEmailAddress, UserRole.CLIENT);
        if (!isEmailAddressUnique) {
            throw new EmailAddressAlreadyInUseException();
        }

        return User.newUniqueUser(currentDateTime, newUserId, clientFullName, clientEmailAddress,
                clientUsername, clientPassword, UserRole.CLIENT);
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
