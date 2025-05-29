package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.UnacceptableDescriptionException;

// also check for Cross-Site Scripting in infrastructure layer

public record Description(String value) {
    public Description {
        boolean validate = value != null && value.length() >= 10 && value.length() <= 500;
        if (!validate) {
            throw new UnacceptableDescriptionException();
        }
    }
}
