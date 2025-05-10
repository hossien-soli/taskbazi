package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.user.domain.exception.PasswordMismatchException;
import dev.hspl.taskbazi.user.domain.service.PasswordProtector;
import dev.hspl.taskbazi.user.domain.value.AuthenticatableUser;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;
import lombok.Getter;

import java.time.LocalDateTime;

// client = normal users of application
// other users = moderator & admin

@Getter
public class Client implements GenericUser, AuthenticatableUser {
    private UserId id;
    private ClientFullName fullName;
    private EmailAddress emailAddress;
    private Username username;
    private ProtectedPassword password;
    private boolean banned;
    private LocalDateTime registeredAt;

    private Integer version; // default=NULL - increments by repository implementations (jpa)
    // Optimistic concurrency control check with front-end clients in the domain layer!!!

    private Client(
            UserId id,
            ClientFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password,
            boolean banned,
            LocalDateTime registeredAt,
            Integer version
    ) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.registeredAt = registeredAt;
        this.version = version;
    }

    // The uniqueness of the email and username must be checked beforehand in a domain service.
    public static Client newUniqueClient(
            LocalDateTime currentDateTime,
            UserId newUserId,
            ClientFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password
    ) {
        return new Client(newUserId,fullName,emailAddress,username,password,false,currentDateTime,null);
    }

    public static Client existingClient(
            UserId id,
            ClientFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password,
            boolean banned,
            LocalDateTime registeredAt,
            Integer version
    ) {
        return new Client(id, fullName, emailAddress, username, password, banned, registeredAt, version);
    }

    @Override
    public UserId genericUserId() {
        return this.id;
    }

    @Override
    public String genericUserFullName() {
        return this.fullName.value();
    }

    @Override
    public EmailAddress genericUserEmailAddress() {
        return this.emailAddress;
    }

    @Override
    public Username genericUserUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountActive() {
        return !this.banned;
    }

    @Override
    public UserRole userRole() {
        return UserRole.CLIENT;
    }

    @Override
    public ProtectedPassword getAuthUserProtectedPassword() {
        return this.password;
    }

    public void updateEmailAddress(EmailAddress uniqueEmailAddress) {
        this.emailAddress = uniqueEmailAddress;
    }

    public void updateUsername(Username uniqueUsername) {
        this.username = uniqueUsername;
    }

    public void changePassword(
            PlainPassword currentPassword,
            PlainPassword newPassword,
            PasswordProtector passwordProtector
    ) {
        boolean matches = passwordProtector.matches(currentPassword,this.password);
        if (!matches) {
            throw new PasswordMismatchException();
        }

        this.password = passwordProtector.protect(newPassword);
    }
}
