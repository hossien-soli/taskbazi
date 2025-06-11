## Technical Features of the Application

### Authentication & Security
- Short-lived JWT access tokens with refresh token rotation mechanism
- API security implemented using Spring Security with custom authentication logic
- Cross-Site Scripting (XSS) protection in API endpoints
- Cryptographically secure random refresh tokens and JWT tokens for authentication

### Architecture
- **Domain-Driven Design (DDD)** in a modular monolith architecture(isolated modules & easily scalable to microservices architecture)
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
- **Primary Database**: PostgreSQL (easily replaceable(even to NoSQL/MongoDB) due to architecture & dependency-inversion)
- **Persistence**: JPA/Hibernate (Spring Data) as infrastructure concern(replaceable to anything!)
- Data integrity ensured through:
  - Relational constraints
  - ACID transactions
- **Schema Management**: Flyway migrations
- **Caching**: Redis implementation

### API & Documentation
- **API Documentation**: OpenAPI specification via springdoc-openapi
- **Localization (i18n)**: API responses and error messages support **English** and **Persian** via Spring's `MessageSource`

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

### Microservices Architecture Migration Guide

> **Note:** Microservices may not be efficient for small-scale applications

#### Architecture Principles
- **Modular Design**: Each package/module (user, task, project, notification) can be converted to a microservice through code reorganization
- **Database Flexibility**: Microservice database connectivity is replaceable by implementing repository interfaces (maintain consistency in distributed systems)
- **API Contracts(Inter-Service Communication)**: Keep existing API interfaces(like EmailSenderAPI); implement HTTP/GRPC version of interfaces for communication between microservices

> Currently, the project module is not **isolated**.

#### Communication Patterns
- **Synchronous Communication**:
  - Keep API interfaces for inter-service direct/read-only calls
  - Implement HTTP/GRPC versions without changing business logic (Dependency Inversion Principle)

- **Asynchronous Communication**:
  - Replace the implementation of `GlobalDomainEventPublisher` with a external message broker implementation
  - Recommended pattern:
    - Outbox pattern for publishers
    - Inbox pattern for consumers (with idempotent operations)
    - Ensures eventual consistency

#### Migration Approach
- **Gateway Setup**:
  - Use the user module as your API gateway
  - Handle authentication centrally and route authenticated-requests to internal/private microservices(task/project/notification)

- **Service Implementation**:
  - Create separate Spring Boot projects for each module(copy existing codes except API interfaces in the root directory of each module)
  - Implement Spring Security for authenticated-user detection from API gateway in each microservice(receive user info from api gateway -> embedded in http request)
  - Adapt existing REST controllers for microservice endpoints(user use-cases)

- **Inter-Service Communication**:
  - Replace module API interfaces with HTTP/GRPC implementations
  - Expose REST endpoints on each service for those API interfaces(inter-service communication endpoints)
  - No need for securing/authorizing inter-service communication endpoints because all services except user are private(private internal network in deployment cluster)

#### Deployment Recommendations
- **Docker Swarm**:
  - Deploy each microservice as a separate service
  - Implement health checks for containers
  - Consider service replication & load balancing(on swarm cluster)

- **Linux Systemd/Supervisor**:
  - Or Run each microservice as a linux systemd service on a linux-server or node(api gateway on public/master node)
  - Implement health checks via:
    - Cron jobs
    - Systemd timers
  - Private network configuration for private microservices(inter-service communication)
  - Of course, you can implement replication without docker

- **Spring-application Replication(Stateless)**:
  - just copy/clone same spring application(or container) in multiple node of the cluster using swarm or other methods

- **Database Replication(Stateful)**:
  - PostgreSQL native replication:
    - Write-Ahead Logging (WAL)
    - Single primary (writes)
    - Multiple read replicas
    - Route queries(write/read) in spring application appropriately

- **Single point/domain/address for accessing application after replication**:
  - Option A: NGINX internal load balancing (risk of single point of failure)
  - Option B: Cloud or external load balancer (recommended/better single point of failure :)))
  - Also use these load balancers or reverse proxies for ssl termination and rate-limiting(security)

#### Replicating modular-monolithic application:
- I think the best choice is custom systemd or supervisor service for each instance/replica of spring application on two or three linux server node
- Implement a health check for each instance/replica using a watchdog script with a cron job or systemd timers.
- Replicate PostgreSQL database using its built-in features like WAL streaming(the best method for this application).
- Use NGINX or an external load balancer(recommended) as a single entry point to the application, distributing traffic/load across multiple Spring app instances.
- Use NGINX for SSL termination, rate limiting, serving static files, caching, and other security-related tasks.
- Alternatively, using simple Docker containers without Swarm mode might be preferable, since Docker provides automatic restart on crashes and built-in health check managing.

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

### 🚧 Project Status
- This project is currently **under development!**

**✅ This file was cleaned and polished with the help of AI.**