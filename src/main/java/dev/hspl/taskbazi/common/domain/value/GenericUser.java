package dev.hspl.taskbazi.common.domain.value;

// a way to track authenticated user in other modules and some other usage

public interface GenericUser {
    UserId genericUserId();

    String genericUserFullName();

    EmailAddress genericUserEmailAddress();

    Username genericUserUsername();

    boolean isAccountActive();

    UserRole userRole();
}
