## Technical Features of the Application

### Authentication & Security
- Short-lived JWT access tokens with refresh token rotation mechanism
- API security implemented using Spring Security with custom authentication logic
- Cross-Site Scripting (XSS) protection in API endpoints
- Cryptographically secure random refresh tokens and JWT tokens for authentication

### Architecture
- **Domain-Driven Design (DDD)** in a modular monolith architecture(isolated modules)
- **Onion Architecture** with three distinct layers per module:
  - Application layer (use cases)
  - Domain layer (business logic)
  - Infrastructure layer (implementations)
- Comprehensive **Dependency Inversion** principle implementation
- **Outbox Pattern** for reliable messaging:
  - Email delivery system
  - Notification dispatching
  - External API call retries
  - Eventual consistency guarantees
  - Optimized database transaction management(shorter transactions due to outboxing)

### Command Processing
- External requests routed through application layer commands & queries
- **CQRS pattern** implementation:
  - Writes enforce business rules through domain layer
  - Reads bypass domain layer as direct database queries for performance

### Event Handling
- Simple event-driven architecture using Spring's application event bus(no external message broker)
- Hybrid module communication (events + direct calls)
- High-throughput asynchronous processing via Java virtual threads

### Data Management
- **Primary Database**: PostgreSQL (easily replaceable due to architecture & dependency-inversion)
- **Persistence**: JPA/Hibernate (Spring Data) as infrastructure concern
- Data integrity ensured through:
  - Relational constraints
  - ACID transactions
- **Schema Management**: Flyway migrations
- **Caching**: Redis implementation

### API & Documentation
- **API Documentation**: OpenAPI specification via springdoc-openapi

### Concurrency Control
- **Optimistic concurrency control**:
  - Versioned entities
  - Client-side integration(Validate the server-side resource version against the client's last-known version)
  - Lost update prevention
- **Pessimistic concurrency control**:
  - PostgreSQL exclusive locks
  - Stronger consistency guarantees
- **Outbox Processor**:
  - Multithreaded email dispatch
  - Pessimistic locking prevents duplicate sends(consistency)

### Notification System
- **Multi-channel delivery**:
  - Persistent database records (queryable history)
  - Email notification delivery
  - Native applications notification delivery such as android applications(push notification)
- **Architectural Features**:
  - Open/Closed Principle compliant (extensible via interfaces)
  - Decoupled from domain logic
  - Domain events trigger notifications
  - Asynchronous processing by default
  - Configurable content formatting (HTML/plain text)

## Business/Domain Features
- Users can register with a valid email address through verification (using a verification code sent to the user's email)
- Users can log in with one or more devices and manage their login sessions
- After registration, users can create their own projects and also join other projects as collaborators
- Users can manage their owned projects and invite other collaborators to their projects
- Each project in TaskBazi has a state (Registered/In Progress/Archived/Completed/Cancelled)
- Owners and other invited collaborators can assign tasks to themselves or other collaborators if permission is granted
- Tasks also have strict states (Assigned/Accepted/In Progress/Completed/Verified/Cancelled/Rejected)
- Collaborators can accept or reject tasks; owners and permitted collaborators can verify or cancel the tasks
- Important events in the application can trigger notifications to users
