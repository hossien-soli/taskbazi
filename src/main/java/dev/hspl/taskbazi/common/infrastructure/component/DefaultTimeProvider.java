package dev.hspl.taskbazi.common.infrastructure.component;

import dev.hspl.taskbazi.common.application.TimeProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultTimeProvider implements TimeProvider {
    @Override
    public LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }
}
