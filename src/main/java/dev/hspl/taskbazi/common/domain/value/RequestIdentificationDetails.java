package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.UnacceptableRequestIdentificationDetailsException;

// ip lookup info + user-agent extracted info or something else for other clients
// https://www.baeldung.com/java-yauaa-user-agent-parsing

public record RequestIdentificationDetails(
        RequestAgentType agentType,
        RequestDeviceType deviceType,
        String deviceIdentifier, // operating system + device model (Android Samsung S25)
        String agentIdentifier,
        // browser-identifier(name / version) or application-identifier(name / version) or cli-identifier

        String countryCode, // from ip-lookup result
        String countryName, // from ip-lookup result
        String cityName // from ip-lookup result
) {
    public RequestIdentificationDetails {
        // for validation -> at least one attribute is required.
        boolean validate = agentType != null || deviceType != null ||
                (deviceIdentifier != null && !deviceIdentifier.isEmpty()) ||
                (agentIdentifier != null && !agentIdentifier.isEmpty()) ||
                (countryCode != null && !countryCode.isEmpty()) ||
                (countryName != null && !countryName.isEmpty()) ||
                (cityName != null && !cityName.isEmpty());

        if (!validate) {
            throw new UnacceptableRequestIdentificationDetailsException();
        }
    }
}
