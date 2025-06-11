package dev.hspl.taskbazi.user.presentation.web.controller.dto;

// needed for translating http request to ClientRegistrationFinalizeCommand
// for having a single point to editing business rules like max length for an email-address...
// ...we should not add validation to these request DTOs that mapping from a json http request body

public record RegistrationFinalizeHttpRequest(
        String registrationSessionId,
        String verificationCode
) {
}
