# Project Structure Documentation

## Refactored Project Structure

```
src/main/java/com/phoebe/pbsub/
├── PbSubApplication.java              # Spring Boot main application class
├── config/                           # Configuration classes
│   ├── DataInitializer.java          # Data initialization configuration
│   └── OpenApiConfig.java            # Swagger/OpenAPI configuration
├── entity/                           # Entity classes (formerly domain)
│   ├── Client.java                   # Client entity
│   ├── ReportSubscription.java       # Report subscription entity
│   └── enums/                        # Enum classes
│       ├── DeliveryMethod.java       # Delivery method enum
│       ├── Frequency.java            # Frequency enum
│       ├── ReportFormat.java         # Report format enum
│       └── ReportType.java           # Report type enum
├── repository/                       # Data access layer (formerly repo)
│   ├── ClientRepository.java         # Client data access interface
│   └── ReportSubscriptionRepository.java # Subscription data access interface
├── service/                          # Business logic layer
│   ├── ClientService.java            # Client business service
│   └── ReportSubscriptionService.java # Subscription business service
├── exception/                        # Exception handling (formerly webadvice)
│   └── GlobalExceptionHandler.java   # Global exception handler
└── web/                              # Web layer
    ├── api/                          # REST API controllers
    │   ├── ClientApi.java            # Client REST API
    │   └── SubscriptionApi.java      # Subscription REST API
    ├── ClientController.java         # Client Web controller
    └── SubscriptionController.java   # Subscription Web controller
```

## Refactoring Notes

### 1. Package Structure Optimization
- **entity**: Renamed from `domain` to `entity`, more consistent with JPA entity naming conventions
- **repository**: Renamed from `repo` to `repository`, more consistent with Spring Data JPA naming conventions
- **exception**: Renamed from `webadvice` to `exception`, more clearly expressing the responsibility of exception handling

### 2. Layered Architecture
The project adopts a standard layered architecture:

```
┌─────────────────┐
│   Web Layer     │ ← Controllers, REST APIs
├─────────────────┤
│  Service Layer  │ ← Business Logic
├─────────────────┤
│Repository Layer │ ← Data Access
├─────────────────┤
│  Entity Layer   │ ← Domain Models
└─────────────────┘
```

### 3. Layer Responsibilities

#### Entity Layer
- Define business entities and enums
- Include JPA annotations and validation annotations
- Handle entity relationship mappings

#### Repository Layer
- Extend Spring Data JPA interfaces
- Provide data access methods
- Support custom queries

#### Service Layer
- Encapsulate business logic
- Handle transaction management
- Provide business methods for controllers to call

#### Web Layer
- **Controllers**: Handle Web requests, return views
- **APIs**: Provide REST API interfaces
- **Exception**: Global exception handling

#### Config Layer
- Application configuration classes
- Data initialization
- Third-party library configuration

## Benefits of Refactoring

1. **Clearer Package Structure**: Each package has clear responsibilities
2. **Compliant with Spring Boot Best Practices**: Follows standard project structure
3. **Better Maintainability**: More reasonable code organization, easier to maintain and extend
4. **Team Collaboration Friendly**: Standardized structure makes it easier for team members to understand and use

## Notes

- All import statements have been updated to new package paths
- Functionality remains completely unchanged
- All tests pass, application runs normally