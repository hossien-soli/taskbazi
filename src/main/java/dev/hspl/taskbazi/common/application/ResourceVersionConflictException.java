package dev.hspl.taskbazi.common.application;

// Optimistic concurrency control mechanism just for validating against client-held resource versions
// This exception is different with jakarta.persistence.OptimisticLockException
// We have another version check just for server-side concurrent threads(handled by JPA/Hibernate)
// Domain-Repository implementations (like JPA) should increment the version after each update of entity/model/resource!!!

// This situation arises when two authenticated users concurrently attempt to modify the same resource.
// proper Http status code: 409
// This error should be handled by front-end clients for users (Please refresh the resource to continue)

public class ResourceVersionConflictException extends ApplicationException {
    public ResourceVersionConflictException() {
        super("Resource modification conflict: multiple users attempting concurrent updates");
    }

    @Override
    public String problemKey() {
        return "frontend_concurrency_problem";
    }
}
