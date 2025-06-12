package dev.hspl.taskbazi.common.presentation.web.component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultHttpRemoteAddressResolver implements HttpRemoteAddressResolver {
    @Override
    public String resolve(HttpServletRequest request) {
        return request.getRemoteAddr(); // or X-Forwarded-For/X-Real-IP headers when spring app is behind a reverse proxy like nginx
    }
}
