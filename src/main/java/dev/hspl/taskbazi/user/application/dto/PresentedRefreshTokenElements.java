package dev.hspl.taskbazi.user.application.dto;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.application.exception.InvalidPresentedRefreshTokenElementsException;
import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;

import java.util.UUID;

public record PresentedRefreshTokenElements(
        UserRole userRole,
        UUID refreshTokenId,
        PlainOpaqueToken plainActualRefreshToken
) {
    public PresentedRefreshTokenElements {
        boolean validate = userRole != null && refreshTokenId != null && plainActualRefreshToken != null;
        if (!validate) {
            throw new InvalidPresentedRefreshTokenElementsException();
        }
    }
}
