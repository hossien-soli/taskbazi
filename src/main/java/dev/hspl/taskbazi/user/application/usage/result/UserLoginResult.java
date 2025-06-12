package dev.hspl.taskbazi.user.application.usage.result;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.domain.value.AccessToken;

public record UserLoginResult(
        PresentedRefreshToken refreshTokenForUser,
        AccessToken accessToken,
        UniversalUser userInfo
) {
}
