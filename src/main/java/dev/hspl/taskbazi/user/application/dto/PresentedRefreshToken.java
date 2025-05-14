package dev.hspl.taskbazi.user.application.dto;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.application.exception.MissingPresentedRefreshTokenException;
import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;
import lombok.Getter;

import java.util.UUID;

// a raw-string mix of user-role + refresh-token-id + plain-actual-refresh-token separated with .
// format: UserRole.UUID.opaqueToken
// it utilize by a user for tracking its login session

public class PresentedRefreshToken {
    @Getter
    private final String rawTokenForUser;

    private PresentedRefreshTokenElements elements;

    private PresentedRefreshToken(
            String rawTokenForUser,
            PresentedRefreshTokenElements elements // nullable
    ) {
        this.rawTokenForUser = rawTokenForUser;
        this.elements = elements;
    }

    public static PresentedRefreshToken buildForUser(PresentedRefreshTokenElements elements) {
        String rawTokenForUser = "%s.%s.%s".formatted(
                elements.userRole(),
                elements.refreshTokenId(),
                elements.plainActualRefreshToken().value()
        );

        return new PresentedRefreshToken(rawTokenForUser,elements);
    }

    public static PresentedRefreshToken fromUserRawPresentedToken(String userRawPresentedToken) {
        boolean validate = userRawPresentedToken != null && !userRawPresentedToken.isEmpty();
        if (!validate) { throw new MissingPresentedRefreshTokenException(); }

        return new PresentedRefreshToken(userRawPresentedToken,parseRawToken(userRawPresentedToken));
    }

    private static PresentedRefreshTokenElements parseRawToken(String userRawPresentedToken) {
        String[] parts = userRawPresentedToken.split("\\.");
        if (parts.length != 3) { throw new MissingPresentedRefreshTokenException(); }

        UserRole userRole;
        try { userRole = UserRole.valueOf(parts[0]); }
        catch (Exception exception) { throw new MissingPresentedRefreshTokenException(); }

        UUID refreshTokenId;
        try { refreshTokenId = UUID.fromString(parts[1]); }
        catch (Exception exception) { throw new MissingPresentedRefreshTokenException(); }

        PlainOpaqueToken plainActualRefreshToken;
        try { plainActualRefreshToken = new PlainOpaqueToken(parts[2]); }
        catch (Exception exception) { throw new MissingPresentedRefreshTokenException(); }

        return new PresentedRefreshTokenElements(userRole,refreshTokenId,plainActualRefreshToken);
    }

    public PresentedRefreshTokenElements getOrParseElements() {
        if (this.elements != null) { return this.elements; }

        this.elements = parseRawToken(this.rawTokenForUser);
        return this.elements;
    }
}
