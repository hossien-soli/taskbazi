package dev.hspl.taskbazi.common.presentation.web;

import java.util.UUID;

public abstract class ControllerUtils {
    protected UUID validateUUIDString(String uuidString) {
        if (uuidString == null || uuidString.length() != 36) {
            throw new InvalidUUIDAsStringException();
        }

        try {
            return UUID.fromString(uuidString);
        } catch (Exception exception) {
            throw new InvalidUUIDAsStringException();
        }
    }
}
