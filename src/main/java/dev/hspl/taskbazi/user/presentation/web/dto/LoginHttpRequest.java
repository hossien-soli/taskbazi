package dev.hspl.taskbazi.user.presentation.web.dto;

public record LoginHttpRequest(
        String usernameOrEmailAddress,
        String password
) {
}
