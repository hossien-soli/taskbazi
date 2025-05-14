package dev.hspl.taskbazi.user.application.usage.result;

import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.domain.value.AccessToken;

public record TokenRefreshResult(
        ...
        PresentedRefreshToken refreshTokenForUser,
        AccessToken accessToken
) {
}
