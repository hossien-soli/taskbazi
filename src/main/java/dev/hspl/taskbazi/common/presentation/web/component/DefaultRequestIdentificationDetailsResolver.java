package dev.hspl.taskbazi.common.presentation.web.component;

import dev.hspl.taskbazi.common.domain.value.RequestIdentificationDetails;
import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import nl.basjes.parse.useragent.UserAgent;
//import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class DefaultRequestIdentificationDetailsResolver implements RequestIdentificationDetailsResolver {
    //private final UserAgentAnalyzer userAgentAnalyzer;
    // !!!!an ip geolocation resolver external api also needed!!!!

    @Override
    @Nullable
    public RequestIdentificationDetails resolve(HttpServletRequest request) {
        //String userAgentHeader = request.getHeader("User-Agent");
        //UserAgent.ImmutableUserAgent parsedUserAgent = userAgentAnalyzer.parse(userAgentHeader);
        // TODO: implement this
        // !!!!an ip geolocation resolver external api also needed!!!!
        return null;
    }
}
