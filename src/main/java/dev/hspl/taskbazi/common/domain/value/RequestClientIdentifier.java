package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.MissingRequestClientIdentifierException;

// usually = ip address
// it should be unique but allow some flexibility -> RequestClientUniqueIdentifier
// be careful with reverse proxies like nginx(same ip)
// for http requests consider using HttpRemoteAddressResolver

public record RequestClientIdentifier(String value) {
    public RequestClientIdentifier {
        boolean validate = value != null && !value.isEmpty() && value.length() <= 50;
        if (!validate) {
            throw new MissingRequestClientIdentifierException();
        }
    }
}
