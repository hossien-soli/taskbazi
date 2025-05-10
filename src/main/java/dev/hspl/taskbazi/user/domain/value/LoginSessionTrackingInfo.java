package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.entity.RefreshToken;

public record LoginSessionTrackingInfo(
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
