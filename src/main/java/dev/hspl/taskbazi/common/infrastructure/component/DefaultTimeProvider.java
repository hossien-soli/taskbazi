package dev.hspl.taskbazi.common.infrastructure.component;

import dev.hspl.taskbazi.common.application.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultTimeProvider implements TimeProvider {
    @Override
    public LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }
}
