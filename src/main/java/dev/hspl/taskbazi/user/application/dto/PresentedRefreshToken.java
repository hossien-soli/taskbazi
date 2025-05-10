package dev.hspl.taskbazi.user.application.dto;

// a raw-string mix of refresh-token-tracker-id and plain-actual-refresh-token separated with .
// format: UUID.opaqueToken

public record PresentedRefreshToken(String rawTokenWithId) {
}
