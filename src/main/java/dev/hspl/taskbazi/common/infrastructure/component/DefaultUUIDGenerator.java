package dev.hspl.taskbazi.common.infrastructure.component;

import dev.hspl.taskbazi.common.application.UUIDGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultUUIDGenerator implements UUIDGenerator {
    @Override
    public UUID generateNew() {
        return UUID.randomUUID();
    }
}
