package dev.hspl.taskbazi.user.domain.value;

public record LoginSessionExtensionResult(
        boolean success,
        TokenRefreshResult refreshResult, // not-null
        LoginSessionTrackingInfo trackingInfo // nullable (only if the refresh-result is successful)
) {
}
