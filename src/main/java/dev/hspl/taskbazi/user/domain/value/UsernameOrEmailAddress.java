package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.exception.UnacceptableUsernameOrEmailAddressException;
import lombok.Getter;

@Getter
public class UsernameOrEmailAddress {
    private final String value;
    private final boolean emailAddress;

    public UsernameOrEmailAddress(String value) {
        boolean isEmailAddress = EmailAddress.validate(value);
        boolean validate = isEmailAddress || Username.validate(value);
        if (!validate) {
            throw new UnacceptableUsernameOrEmailAddressException();
        }

        this.value = value.toLowerCase();
        this.emailAddress = isEmailAddress;
    }

    public EmailAddress asEmailAddress() {
        return new EmailAddress(value);
    }

    public Username asUsername() {
        return new Username(value);
    }
}
