package dev.hspl.taskbazi.user.application.usage.result;

import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.domain.value.AccessToken;
import dev.hspl.taskbazi.user.domain.value.TokenRefreshResult;

public record TokenRotationResult(
        boolean success,
        TokenRefreshResult refreshResult,
        PresentedRefreshToken newRefreshToken, // nullable - only if success is true
        AccessToken newAccessToken // nullable - only if success is true
) {
}
