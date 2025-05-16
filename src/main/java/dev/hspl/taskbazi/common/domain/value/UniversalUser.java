package dev.hspl.taskbazi.common.domain.value;

// a way to track authenticated user in other modules and some other usage

public interface UniversalUser {
    UserId universalUserId();

    String universalUserFullName();

    EmailAddress universalUserEmailAddress();

    Username universalUserUsername();

    boolean isAccountActive();

    UserRole userRole();
}
