package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.InvalidEmailAddressException;

import java.util.regex.Pattern;

public record EmailAddress(String value) {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean validate(String emailAddress) {
        return emailAddress != null && !emailAddress.isEmpty()
                && emailAddress.length() <= 60 && EMAIL_PATTERN.matcher(emailAddress).matches();
    }

    public EmailAddress {
        if (!validate(value)) {
            throw new InvalidEmailAddressException();
        }

        value = value.toLowerCase();
    }
}
