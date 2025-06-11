package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationFinalizeCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationFinalizeResult;

// Possible predictable exceptions in this use-case:
// InvalidApplicationCommandException
// UnacceptableVerificationCodeException, MissingRequestClientIdentifierException
// InvalidRegistrationSessionIdException, ClosedRegistrationSessionException
// RequestClientIdentifierMismatchException, BadSessionStateRegistrationException
// UsernameAlreadyInUseException, EmailAddressAlreadyInUseException

public interface ClientRegistrationFinalizeUseCase {
    ClientRegistrationFinalizeResult execute(ClientRegistrationFinalizeCommand command);
}
