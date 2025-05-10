package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.UnacceptableAccountUsernameException;

import java.util.regex.Pattern;

public record Username(String value) {
    private static final String USERNAME_REGEX = "^(?!.*(__|--))[a-zA-Z0-9_-]+$";
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

    public static boolean validate(String username) {
        return username != null && username.length() >= 5 && username.length() <= 30
                && USERNAME_PATTERN.matcher(username).matches();
    }

    public Username {
        if (!validate(value)) {
            throw new UnacceptableAccountUsernameException();
        }

        value = value.toLowerCase();
    }
}
