# PB Client Report Subscription System

## Technical Documentation

This document provides comprehensive technical documentation for the PB Client Report Subscription System, a Spring Boot-based application for managing client report subscriptions in investment banking and financial institutions.

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technical Architecture](#technical-architecture)
3. [System Features](#system-features)
4. [Database Design](#database-design)
5. [API Documentation](#api-documentation)
6. [Frontend Interface](#frontend-interface)
7. [Deployment Instructions](#deployment-instructions)
8. [Development Guide](#development-guide)

---

## Project Overview

### Project Description
PB Client Report Subscription System is a Spring Boot-based client report subscription management system, designed specifically for investment banks and financial institutions to manage the complete lifecycle of client report subscriptions.

### Core Features
- **Client Management**: Complete CRUD operations for client information
- **Subscription Management**: Create, edit, delete, and query report subscriptions
- **Multi-format Support**: Support for PDF, CSV, Excel, XML and other report formats
- **Multiple Delivery Methods**: Support for Email, FTP, SFTP, API and other delivery methods
- **Frequency Control**: Support for daily, weekly, monthly, quarterly, yearly and other frequencies
- **RESTful API**: Complete REST API interface
- **Web Interface**: User-friendly Web management interface

### Tech Stack
- **Backend Framework**: Spring Boot 3.5.4
- **Database**: H2 (In-memory database, supports persistence)
- **ORM**: JPA/Hibernate
- **Frontend**: Thymeleaf + HTML/CSS/JavaScript
- **API Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven
- **Java Version**: JDK 17

---

## Technical Architecture

### Layered Architecture Design

The system adopts a standard layered architecture to ensure code maintainability and scalability:

```
┌─────────────────────────────────────┐
│           Presentation Layer        │ ← Web Controllers, REST APIs
├─────────────────────────────────────┤
│           Business Layer            │ ← Service Classes
├─────────────────────────────────────┤
│           Data Access Layer         │ ← Repository Interfaces
├─────────────────────────────────────┤
│           Entity Layer              │ ← JPA Entities, Enums
└─────────────────────────────────────┘
```

### Package Structure

```
src/main/java/com/phoebe/pbsub/
├── config/                    # Configuration classes
│   ├── DataInitializer.java   # Data initialization
│   └── OpenApiConfig.java     # API documentation configuration
├── entity/                    # Entity classes
│   ├── Client.java           # Client entity
│   ├── ReportSubscription.java # Subscription entity
│   └── enums/                # Enum classes
│       ├── DeliveryMethod.java
│       ├── Frequency.java
│       ├── ReportFormat.java
│       └── ReportType.java
├── repository/               # Data access layer
│   ├── ClientRepository.java
│   └── ReportSubscriptionRepository.java
├── service/                  # Business logic layer
│   ├── ClientService.java
│   └── ReportSubscriptionService.java
├── exception/                # Exception handling
│   ├── ClientNotFoundException.java
│   ├── SubscriptionNotFoundException.java
│   ├── DuplicateSubscriptionException.java
│   └── GlobalExceptionHandler.java
└── web/                      # Web layer
    ├── api/                  # REST API controllers
    │   ├── ClientApi.java
    │   └── SubscriptionApi.java
    ├── ClientController.java
    └── SubscriptionController.java
```

---

## System Features

### 1. Client Management Features

#### Feature Characteristics
- **Client Registration**: Support for basic client information entry
- **Client Query**: Support for fuzzy search by name
- **Client Editing**: Support for client information updates
- **Client Deletion**: Support for client and associated subscription deletion
- **Subscription Association**: Automatic management of client-subscription relationships

#### Business Rules
- Client name is required
- Email format validation
- Cascade delete all subscriptions when deleting client

### 2. Subscription Management Features

#### Feature Characteristics
- **Subscription Creation**: Support for creating report subscriptions for specified clients
- **Subscription Editing**: Support for modifying subscription parameters
- **Subscription Deletion**: Support for deleting specified subscriptions
- **Subscription Query**: Support for querying subscription lists by client

#### Subscription Parameters
- **Report Type**: Custom report type names
- **Frequency**: Daily, Weekly, Monthly, Quarterly, Yearly
- **Format**: PDF, CSV, Excel, XML
- **Delivery Method**: Email, FTP, SFTP, API

### 3. Data Validation

#### Client Validation
- Required field validation
- Email format validation
- Data length restrictions

#### Server-side Validation
- Use Bean Validation annotations
- Custom validation rules
- Friendly error messages

---

## Database Design

### Table Structure

#### 1. clients Table

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Client ID |
| name | VARCHAR(255) | NOT NULL | Client name |
| email | VARCHAR(255) | NULL | Client email |

**Field Description**:
- `id`: Primary key, auto-increment
- `name`: Client name, required
- `email`: Client email, optional

#### 2. report_subscriptions Table

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Subscription ID |
| client_id | BIGINT | NOT NULL, FOREIGN KEY | Client ID |
| report_type | VARCHAR(50) | NOT NULL | Report type |
| frequency | VARCHAR(20) | NOT NULL | Generation frequency |
| format | VARCHAR(20) | NOT NULL | Report format |
| delivery_method | VARCHAR(20) | NOT NULL | Delivery method |

**Field Description**:
- `id`: Primary key, auto-increment
- `client_id`: Foreign key, references client table
- `report_type`: Report type
- `frequency`: Generation frequency
- `format`: Report format
- `delivery_method`: Delivery method

### Relationship Design
- **One-to-Many Relationship**: One client can have multiple subscriptions
- **Cascade Delete**: Automatically delete related subscriptions when deleting client
- **Foreign Key Constraints**: Ensure data integrity

---

## API Documentation

### Client Management API

#### 1. Get All Clients
```
GET /api/clients
```

**Response Example**:
```json
[
  {
    "id": 1,
    "name": "Goldman Sachs Investment Management",
    "email": "gsim@gs.com",
    "subscriptions": [
      {
        "id": 1,
        "reportType": "TRADE_CONFIRM",
        "frequency": "DAILY",
        "format": "PDF",
        "deliveryMethod": "EMAIL"
      }
    ]
  }
]
```

#### 2. Get Client by ID
```
GET /api/clients/{id}
```

#### 3. Get Client with Subscriptions
```
GET /api/clients/{id}/with-subscriptions
```

#### 4. Create New Client
```
POST /api/clients
Content-Type: application/json

{
  "name": "New Client",
  "email": "client@example.com"
}
```

#### 5. Update Client Information
```
PUT /api/clients/{id}
Content-Type: application/json

{
  "name": "Updated Client Name",
  "email": "updated@example.com"
}
```

#### 6. Delete Client
```
DELETE /api/clients/{id}
```

#### 7. Search Clients
```
GET /api/clients/search?name=Goldman
```

#### 8. Get Client Statistics
```
GET /api/clients/stats
```

### Subscription Management API

#### 1. Get All Subscriptions
```
GET /api/subscriptions
```

#### 2. Get Subscriptions by Client ID
```
GET /api/clients/{clientId}/subscriptions
```

#### 3. Get Subscription by ID
```
GET /api/subscriptions/{id}
```

#### 4. Create New Subscription
```
POST /api/clients/{clientId}/subscriptions
Content-Type: application/json

{
  "reportType": "DAILY_PNL",
  "frequency": "DAILY",
  "format": "PDF",
  "deliveryMethod": "EMAIL"
}
```

#### 5. Update Subscription
```
PUT /api/subscriptions/{id}
Content-Type: application/json

{
  "reportType": "MONTHLY_STATEMENT",
  "frequency": "MONTHLY",
  "format": "CSV",
  "deliveryMethod": "FTP"
}
```

#### 6. Delete Subscription
```
DELETE /api/subscriptions/{id}
```

---

## Frontend Interface

### Page Structure

#### 1. Client List Page (`/clients`)
- **Function**: Display all client list
- **Operations**: View, edit, delete, add subscription
- **Features**: Responsive design, supports empty state display

#### 2. Client Detail Page (`/clients/{id}`)
- **Function**: Display client detailed information and subscription list
- **Operations**: Edit client, add subscription, edit subscription, delete subscription
- **Features**: Real-time display of subscription count

#### 3. Client Form Page (`/clients/new`, `/clients/{id}/edit`)
- **Function**: Create or edit client information
- **Validation**: Client-side and server-side dual validation
- **Features**: Friendly error messages

#### 4. Subscription Form Page (`/clients/{id}/subscriptions/new`, `/clients/{id}/subscriptions/{id}/edit`)
- **Function**: Create or edit subscription information
- **Options**: Dropdown selection for frequency, format, delivery method
- **Features**: Dynamic form validation

### User Interface Features

#### Design Principles
- **Clear and Simple**: Clear page layout and navigation
- **User Friendly**: Intuitive operation flow
- **Responsive**: Support different screen sizes
- **Consistent**: Unified visual style

#### Interactive Features
- **Real-time Feedback**: Success/failure operation prompts
- **Confirmation Dialogs**: Delete operation confirmation
- **Form Validation**: Real-time input validation
- **Convenient Navigation**: Breadcrumb navigation

---

## Deployment Instructions

### Environment Requirements
- **Java**: JDK 17 or higher
- **Memory**: Minimum 512MB
- **Disk**: Minimum 100MB

### Build Steps

#### 1. Clone Project
```bash
git clone <repository-url>
cd GCF_Final_Project_ReportingSystem
```

#### 2. Compile Project
```bash
mvn clean compile
```

#### 3. Run Tests
```bash
mvn test
```

#### 4. Package Application
```bash
mvn clean package
```

#### 5. Run Application
```bash
java -jar target/pb-client-reporting-subscription-1.0.0.jar
```

### Configuration

#### application.yml Configuration
```yaml
spring:
  application:
    name: pb-client-reporting-subscription
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080

logging:
  level:
    com.phoebe.pbsub: DEBUG
```

### Access URLs
- **Web Interface**: http://localhost:8080/clients
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Database Console**: http://localhost:8080/h2-console

---

## Development Guide

### Development Environment Setup

#### 1. IDE Configuration
- **Recommended IDE**: IntelliJ IDEA or Eclipse
- **Plugins**: Spring Boot, Maven, Lombok (optional)

#### 2. Project Import
1. Import Maven project
2. Wait for dependency download completion
3. Configure JDK 17
4. Run main class `PbSubApplication`

### Code Standards

#### 1. Naming Conventions
- **Class Names**: PascalCase (e.g., `ClientService`)
- **Method Names**: camelCase (e.g., `findById`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Package Names**: All lowercase (e.g., `com.phoebe.pbsub.service`)

#### 2. Comment Standards
- **Class Comments**: Explain class responsibilities and usage
- **Method Comments**: Explain method functionality, parameters, and return values
- **Complex Logic**: Add inline comments for explanation

#### 3. Exception Handling
- Use global exception handler
- Provide friendly error messages
- Log detailed error information

### Extension Development

#### 1. Add New Entity
1. Create entity class in `entity` package
2. Add JPA annotations and validation annotations
3. Create corresponding Repository interface
4. Create Service class to handle business logic
5. Create Controller to provide API interface

#### 2. Add New Features
1. Add business methods in Service layer
2. Add Web interfaces in Controller layer
3. Add REST interfaces in API layer
4. Update frontend pages
5. Add unit tests

#### 3. Database Migration
- Use Hibernate's `ddl-auto: update`
- Production environment recommends using Flyway or Liquibase

### Testing Strategy

#### 1. Unit Tests
- Test Service layer business logic
- Test Repository layer data access
- Test Controller layer Web interfaces

#### 2. Integration Tests
- Test complete business processes
- Test database operations
- Test API interfaces

#### 3. Frontend Tests
- Test user interface interactions
- Test form validation
- Test page navigation

---

## Project Highlights

### 1. Technical Highlights
- **Modern Tech Stack**: Uses latest Spring Boot 3.x
- **RESTful Design**: Complete REST API design
- **Data Validation**: Comprehensive input validation mechanism
- **Exception Handling**: Unified exception handling strategy
- **API Documentation**: Auto-generated Swagger documentation

### 2. Architecture Highlights
- **Layered Architecture**: Clear layered design
- **Loose Coupling**: Low coupling between layers
- **High Cohesion**: Clear responsibilities for each layer
- **Extensible**: Easy to extend new features

### 3. User Experience
- **Responsive Design**: Adapt to different devices
- **Friendly Interface**: Intuitive user interface
- **Real-time Feedback**: Timely operation feedback
- **Error Handling**: Friendly error messages

---

## Summary

The PB Client Report Subscription System is a comprehensive Spring Boot application that demonstrates modern Java development best practices. With its layered architecture, complete API documentation, comprehensive testing, and user-friendly interface, it provides a solid foundation for managing client report subscriptions in financial institutions.

The system successfully combines backend business logic with frontend user interface, providing both REST API and Web interface for different user needs. Through proper exception handling, data validation, and testing strategies, it ensures system stability and reliability.

This project serves as an excellent example of enterprise-level Spring Boot application development, showcasing how to build scalable, maintainable, and user-friendly business applications.