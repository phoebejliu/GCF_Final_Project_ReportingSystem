# PB Client Reporting Subscription System

A comprehensive Spring Boot application for managing Prime Brokerage client report subscriptions, developed as a Java Bootcamp Final Project.

## 🎯 Project Overview

This system allows PB clients to subscribe to various types of reports with configurable delivery methods, frequencies, and formats. It provides both a web interface and REST API for complete client and subscription management.

## 🚀 Features

### Core Functionality
- **Client Management**: Complete CRUD operations for PB clients
- **Report Subscription Management**: Configure report types, frequencies, formats, and delivery methods
- **Multi-format Support**: PDF and CSV report formats
- **Multiple Delivery Methods**: Email, UI display, and FTP transfer
- **Flexible Frequencies**: Daily, Weekly, and Monthly report generation

### Technical Features
- **REST API**: Full RESTful API with Swagger documentation
- **Web Interface**: Modern, responsive Thymeleaf-based UI
- **Data Validation**: Comprehensive input validation and error handling
- **Database Integration**: H2 in-memory database with JPA/Hibernate
- **Exception Handling**: Global exception handling for both API and web requests

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.4
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Web Framework**: Thymeleaf
- **API Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven
- **Java Version**: 21

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Git

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd GCF_Final_Project_ReportingSystem
```

### 2. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### 3. Access the Application
- **Web Interface**: http://localhost:8080/clients
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## 📊 Demo Data

The application automatically initializes with demo data including:
- **4 Sample Clients**: Goldman Sachs, JPMorgan, BlackRock, Bridgewater
- **Multiple Subscriptions**: Various report types with different configurations
- **Realistic Data**: Professional client names and contact information

## 🔧 API Endpoints

### Client Management
- `GET /api/clients` - List all clients
- `POST /api/clients` - Create new client
- `GET /api/clients/{id}` - Get client details
- `PUT /api/clients/{id}` - Update client
- `DELETE /api/clients/{id}` - Delete client

### Subscription Management
- `GET /api/clients/{clientId}/subscriptions` - List client subscriptions
- `POST /api/clients/{clientId}/subscriptions` - Create subscription
- `PUT /api/subscriptions/{id}` - Update subscription
- `DELETE /api/subscriptions/{id}` - Delete subscription

## 🎨 Web Interface

### Client Management
- **Client List**: View all clients with search functionality
- **Client Details**: View client information and their subscriptions
- **Add/Edit Client**: Form-based client management
- **Delete Client**: Remove clients with confirmation

### Subscription Management
- **Create Subscription**: Configure report subscriptions for clients
- **Edit Subscription**: Modify existing subscription settings
- **Toggle Status**: Activate/deactivate subscriptions
- **Delete Subscription**: Remove subscriptions

## 📝 Report Types

- **Trade Confirmation**: Daily trade confirmations
- **Options Expiry**: Options expiration reports
- **Margin Call**: Margin call notifications
- **Account Statement**: Monthly account statements
- **Daily P&L**: Daily profit and loss reports

## 🔄 Delivery Methods

- **Email**: Direct email delivery
- **UI**: Display in web interface
- **FTP**: File transfer to specified FTP path

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/phoebe/pbsub/
│   │   ├── config/          # Configuration classes
│   │   ├── domain/          # JPA entities and enums
│   │   ├── repo/            # Repository interfaces
│   │   ├── service/         # Business logic services
│   │   ├── web/             # Thymeleaf controllers
│   │   ├── web/api/         # REST API controllers
│   │   └── webadvice/       # Global exception handling
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       └── application.yml  # Application configuration
└── test/                    # Test classes
```

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📚 Documentation

- **API Documentation**: Available at `/swagger-ui.html` when running
- **Code Documentation**: Comprehensive JavaDoc comments throughout the codebase
- **Configuration**: See `application.yml` for database and application settings

## 🔒 Security Considerations

- Input validation on all forms and API endpoints
- SQL injection protection through JPA
- XSS protection through Thymeleaf
- Global exception handling prevents information leakage

## 🚀 Deployment

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/pb-client-reporting-subscription-1.0.0.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is developed as part of a Java Bootcamp Final Project for educational purposes.

## 👥 Team

**FX Prime Brokerage Team**
- Java Bootcamp Final Project
- Version: 1.0.0

## 🆘 Support

For questions or issues:
1. Check the API documentation at `/swagger-ui.html`
2. Review the application logs
3. Check the database console at `/h2-console`

---

**Note**: This is a demonstration application with demo data. In a production environment, proper security measures, database configuration, and deployment strategies should be implemented.