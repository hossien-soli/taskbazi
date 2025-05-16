package dev.hspl.taskbazi.user.domain.entity;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainNotificationRequestEvent;
import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.user.domain.event.ClientRegisteredDomainEvent;
import dev.hspl.taskbazi.user.domain.exception.PasswordMismatchException;
import dev.hspl.taskbazi.user.domain.service.PasswordProtector;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;
import dev.hspl.taskbazi.user.domain.value.UserFullName;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User extends DomainAggregateRoot implements UniversalUser {
    private final UserId id;
    private UserFullName fullName;
    private EmailAddress emailAddress;
    private Username username;
    private ProtectedPassword password;
    private boolean banned;
    private final LocalDateTime registeredAt;

    private UserRole role;

    private final Integer version; // default=NULL - increments by repository implementations (jpa)
    // Optimistic concurrency control check with front-end clients in the domain layer!!!

    private User(
            UserId id,
            UserFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password,
            boolean banned,
            LocalDateTime registeredAt,
            UserRole role,
            Integer version
    ) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.registeredAt = registeredAt;
        this.role = role;
        this.version = version;
    }

    // The uniqueness of the email and username must be checked beforehand in a domain service.
    public static User newUniqueUser(
            LocalDateTime currentDateTime,
            UserId newUserId,
            UserFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password,
            UserRole role
    ) {
        User result = new User(newUserId, fullName, emailAddress, username, password, false, currentDateTime, role, null);

        if (role.equals(UserRole.CLIENT)) {
            DomainNotificationRequestEvent notifRequestEvent = new ClientRegisteredDomainEvent(
                    currentDateTime,
                    User.class.getSimpleName(),
                    newUserId.value(),
                    newUserId,
                    emailAddress,
                    fullName,
                    username
            );

            result.registerDomainEvent(notifRequestEvent);
        }

        return result;
    }

    public static User existingUser(
            UserId id,
            UserFullName fullName,
            EmailAddress emailAddress,
            Username username,
            ProtectedPassword password,
            boolean banned,
            LocalDateTime registeredAt,
            UserRole role,
            Integer version
    ) {
        return new User(id, fullName, emailAddress, username, password, banned, registeredAt, role, version);
    }

    @Override
    public UserId universalUserId() {
        return this.id;
    }

    @Override
    public String universalUserFullName() {
        return this.fullName.value();
    }

    @Override
    public EmailAddress universalUserEmailAddress() {
        return this.emailAddress;
    }

    @Override
    public Username universalUserUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountActive() {
        return !this.banned;
    }

    @Override
    public UserRole userRole() {
        return this.role;
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
        boolean matches = passwordProtector.matches(currentPassword, this.password);
        if (!matches) {
            throw new PasswordMismatchException();
        }

        this.password = passwordProtector.protect(newPassword);
        //registerDomainEvent(); password changed notif event
    }
}
