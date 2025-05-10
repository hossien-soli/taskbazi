package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;
import dev.hspl.taskbazi.user.domain.value.ProtectedOpaqueToken;

// we use bcrypt to hash refresh tokens for storing them inside the database
// because of bcrypt token hashing we need to send the id of the refresh token tracker or record to the clients
// maybe due to flexibility we need to create a dedicated contract or interface for token verifying

public interface OpaqueTokenProtector {
    ProtectedOpaqueToken protect(PlainOpaqueToken plainOpaqueToken);
    boolean matches(PlainOpaqueToken plainOpaqueToken, ProtectedOpaqueToken protectedOpaqueToken);
}
