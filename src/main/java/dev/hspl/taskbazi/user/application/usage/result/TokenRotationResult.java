package dev.hspl.taskbazi.user.application.usage.result;

import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.domain.value.AccessToken;

public record TokenRotationResult(
        ...
        PresentedRefreshToken refreshTokenForUser,
        AccessToken accessToken
) {
}
