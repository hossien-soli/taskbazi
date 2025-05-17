package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.TokenRotationCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRotationResult;

// Potential use-case exceptions:
//   Domain -> UserRoleMismatchLoginException(impl)
//   Application -> InvalidRefreshTokenIdException(user), UserNotFoundLoginException(user)
//   Application -> MissingPresentedRefreshTokenException(user)

public interface TokenRotationUseCase {
    TokenRotationResult execute(TokenRotationCommand command);
}
