package dev.hspl.taskbazi.common.application;

import java.util.UUID;

// generates universally unique identifier for entities
// it should be implemented in the application layer
// The domain layer shouldn't be aware of how UUIDs are generated

public interface UUIDGenerator {
    UUID generateNew();
}
