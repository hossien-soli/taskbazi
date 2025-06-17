package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationRequestCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationRequestResult;
import org.springframework.lang.NonNull;

// Possible predictable exceptions in this use-case:
// InvalidApplicationCommandException
// UnacceptableUserFullNameException, UnacceptableAccountUsernameException
// PasswordConfirmationMismatchException, UnacceptablePlainPasswordException
// InvalidEmailAddressException, MissingRequestClientIdentifierException
// UnacceptableVerificationCodeException, UsernameAlreadyInUseException
// EmailAddressAlreadyInUseException, RegistrationSessionRestrictionException
// MissingProtectedPasswordException, MissingProtectedVerificationCodeException

public interface ClientRegistrationRequestUseCase {
    ClientRegistrationRequestResult execute(@NonNull ClientRegistrationRequestCommand command);
}
