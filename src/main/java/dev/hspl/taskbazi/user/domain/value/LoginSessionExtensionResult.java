package dev.hspl.taskbazi.user.domain.value;

public record LoginSessionExtensionResult(
        TokenRefreshResult refreshResult, // not-null
        LoginSessionTrackingInfo trackingInfo // nullable (only if the refresh-result is successful)
) {
}
