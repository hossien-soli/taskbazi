package dev.hspl.taskbazi.user.application.service;

import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;

public interface OpaqueTokenGenerator {
    PlainOpaqueToken generateNew(int numberOfRandomBytes);
}
