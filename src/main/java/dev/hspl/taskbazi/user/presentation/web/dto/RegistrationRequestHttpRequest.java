package dev.hspl.taskbazi.user.presentation.web.dto;

// needed for translating http request to ClientRegistrationRequestCommand
// for having a single point to editing business rules like max length for an email-address...
// ...we should not add validation to these request DTOs that mapping from a json http request body

public record RegistrationRequestHttpRequest(
        String fullName,
        String emailAddress,
        String username,
        String password,
        String passwordConfirmation
) {
}
