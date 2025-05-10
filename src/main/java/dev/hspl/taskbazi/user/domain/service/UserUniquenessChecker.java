package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;

public interface UserUniquenessChecker {
    boolean checkEmailAddressIsUnique(EmailAddress emailAddress);
    boolean checkUsernameIsUnique(Username username);
}
