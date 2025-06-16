package dev.hspl.taskbazi.common.application.exception;

import lombok.Getter;

// Optimistic concurrency control mechanism just for validating against client-held entity/resource versions
// This exception is different with jakarta.persistence.OptimisticLockException
// We have another version check just for server-side concurrent actions(threads) (handled by JPA/Hibernate)
// Domain-Repository implementations (like JPA) should increment the version after each update of entity/model/resource!!!

// This situation arises when two authenticated users(client-side) concurrently attempt to modify the same resource.
// proper Http status code: 409
// This error should be handled by front-end clients for users (Please refresh the resource to continue)

// We don't use ETag and If-Match or standard Precondition headers!
// we use a custom header for getting version as a metadata from the client(X-Entity-Version)
// and sending version in the response json body (Not ETag)

@Getter
public class EntityVersionConflictException extends ApplicationException {
    private final int actualVersion;

    public EntityVersionConflictException(int actualVersion) {
        super("Entity modification conflict: multiple users(clients) attempting concurrent updates");
        this.actualVersion = actualVersion;
    }

    @Override
    public String problemKey() {
        return "frontend_concurrency_problem";
    }

    @Override
    public short groupingValue() {
        return 409;
    }
}
