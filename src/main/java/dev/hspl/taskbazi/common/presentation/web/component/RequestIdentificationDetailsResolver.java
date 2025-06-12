package dev.hspl.taskbazi.common.presentation.web.component;

import dev.hspl.taskbazi.common.domain.value.RequestIdentificationDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;

public interface RequestIdentificationDetailsResolver {
    @Nullable
    RequestIdentificationDetails resolve(HttpServletRequest request);
    // can be null if failed to resolve!
}
