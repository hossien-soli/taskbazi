package dev.hspl.taskbazi.user.presentation.web.dto;

// refresh token rotation

public record TokenRotationHttpRequest(
        String refreshToken
) {
}
