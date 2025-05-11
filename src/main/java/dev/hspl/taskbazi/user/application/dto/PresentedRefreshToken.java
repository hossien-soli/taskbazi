package dev.hspl.taskbazi.user.application.dto;

import dev.hspl.taskbazi.user.application.exception.MissingPresentedRefreshTokenException;

// a raw-string mix of user-role + refresh-token-id + plain-actual-refresh-token separated with .
// format: UserRole.UUID.opaqueToken

public record PresentedRefreshToken(String rawTokenWithId) {
    public PresentedRefreshToken {
        boolean validate = rawTokenWithId != null && !rawTokenWithId.isEmpty();
        if (!validate) {
            throw new MissingPresentedRefreshTokenException();
        }
    }
}
