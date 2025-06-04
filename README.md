## Technical Features of the Application

### Authentication & Security
- Short-lived JWT access tokens with refresh token rotation mechanism
- API security implemented using Spring Security with custom authentication
- Protection against Cross-Site Scripting (XSS) in API endpoints
- Cryptographically secure random refresh tokens and JWT tokens for authentication

### Architecture
- Domain-Driven Design (DDD) in a modular-monolithic architecture
- Onion Architecture implementation with three layers per module:
    - Application layer
    - Domain layer
    - Infrastructure layer
- Comprehensive use of Dependency Inversion principle for flexibility

### Command Processing
- External requests processed through application layer use-cases and commands
- CQRS pattern implementation:
    - Writes enforce business rules through domain layer
    - Reads bypass domain layer as direct database queries for performance

### Event Handling
- Simple event-driven design using Spring event bus (no external message broker)
- Module communication through both events and direct calls
- Asynchronous processing using Java virtual threads for high-throughput
- Outbox Pattern implementation for:
    - Email sending
    - External API calls
    - Eventual consistency
    - Shorter database transactions

### Data Management
- Primary database: PostgreSQL (easily replaceable due to architecture)
- JPA (Spring Data) as infrastructure concern for database access
- Effective use of relational database constraints and transactions
- Flyway migrations for database schema management (DDL)
- Redis for caching implementation

### API & Documentation
- API documentation using OpenAPI with springdoc library

### Concurrency Control
- Optimistic concurrency control:
    - Version-based entities
    - Client-side integration(Validate the server-side resource version against the client's last-known version)
    - Prevents lost-update problems
- Pessimistic concurrency control:
    - PostgreSQL exclusive locks
    - Stronger consistency guarantees
- Outbox pattern scheduled jobs:
    - Multithreaded email sending
    - Pessimistic locking prevents duplicate sends