# Application Architecture and Flow

## Overview

This project is a Spring Boot web application built as a standard layered backend service. It exposes REST APIs, performs business logic in a service layer, persists data through JPA repository abstractions, and secures endpoints with JWT-based authentication.

The design is intentionally generic and can apply to many Spring Boot applications that follow a controller → service → repository pattern.

---

## High-Level Architecture

1. **Presentation Layer (Controller Layer)**
   - Exposes REST endpoints via Spring MVC controllers.
   - Receives HTTP requests from clients such as Postman, browser, or frontend apps.
   - Delegates request processing to service methods.
   - Sends HTTP responses back to the client.

2. **Service Layer**
   - Implements business logic and use cases.
   - Validates input data and applies domain rules.
   - Calls persistence layer methods to read or write data.
   - Translates repository results to response payloads.

3. **Persistence Layer (Repository Layer)**
   - Uses Spring Data JPA repositories to interact with the database.
   - Provides standard CRUD operations without custom DAO boilerplate.
   - Manages entity persistence, retrieval, updates, and deletes.

4. **Security Layer**
   - Secures endpoints using Spring Security.
   - Adds a custom JWT authentication filter into the security filter chain.
   - Allows unauthenticated access only to authentication endpoints.
   - Validates JWT tokens for protected requests.

5. **Exception Handling**
   - Centralized global exception handler captures exceptions across controllers.
   - Converts exceptions into structured HTTP error responses.
   - Handles validation errors, data integrity violations, missing resources, and generic server errors.

---

## Runtime Request Flow

1. **Application startup**
   - The application boots via `SpringApplication.run(...)` in the main class.
   - Spring Boot auto-configures web, data, security, and other application components.

2. **Incoming HTTP request**
   - A request reaches the Spring MVC dispatcher and is matched to a controller endpoint.
   - If the request is for an authentication endpoint, it is permitted without a JWT token.
   - Otherwise, the JWT authentication filter evaluates the `Authorization: Bearer <token>` header.

3. **JWT Authentication Filter**
   - Reads the incoming `Authorization` header.
   - If a valid token is found, the filter extracts identity information from the token.
   - The filter sets authentication into `SecurityContextHolder` for downstream access control.
   - If the token is invalid or missing, the request is blocked by Spring Security.

4. **Controller processing**
   - Controller methods parse request parameters and request bodies.
   - Data validation annotations ensure required fields and formatting rules are enforced.
   - The controller delegates to service layer methods.

5. **Service logic and persistence**
   - Service methods perform validation and business operations.
   - The underlying repository executes database queries using JPA.
   - Entities are mapped to database tables and persisted.

6. **Response generation**
   - The service returns a domain object or result to the controller.
   - The controller returns the object as JSON via HTTP response.
   - If any exception occurs, the global exception handler formats a structured error response.

---

## Core Technology Stack

- Java 17
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JSON Web Tokens (JWT) using `jjwt`
- PostgreSQL JDBC driver
- OpenAPI UI for API documentation via `springdoc-openapi`
- Lombok for boilerplate reduction
- Spring Boot DevTools for development convenience
- JUnit / Mockito for testing support
- JaCoCo for test coverage reporting

---

## Main Libraries and Dependencies

- `org.springframework.boot:spring-boot-starter-webmvc`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-security`
- `io.jsonwebtoken:jjwt-api`
- `io.jsonwebtoken:jjwt-impl`
- `io.jsonwebtoken:jjwt-jackson`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.postgresql:postgresql`
- `org.projectlombok:lombok`
- `org.springframework.boot:spring-boot-devtools`
- `org.springframework.boot:spring-boot-starter-data-jpa-test`
- `org.springframework.boot:spring-boot-starter-webmvc-test`
- `org.mockito:mockito-core`
- `org.mockito:mockito-junit-jupiter`
- `org.jacoco:jacoco-maven-plugin`

---

## Configuration Summary

- `server.port=8082`
- PostgreSQL datasource configuration with JDBC URL, username, password, driver class name.
- `spring.jpa.hibernate.ddl-auto=update` to manage schema updates automatically.
- `spring.jpa.show-sql=true` to log SQL statements.
- JWT secret and expiration configured in properties.

---

## Architectural Patterns

- **Layered architecture**: clear separation between controllers, services, and repositories.
- **Dependency injection**: Spring manages component wiring using `@Autowired`, `@Service`, `@Repository`, and `@Component`.
- **RESTful API design**: controllers expose CRUD-style endpoints for domain operations.
- **JWT-based stateless security**: authentication is handled through bearer tokens.
- **Global exception handling**: error responses are standardized across the application.

---

## Notes

- The persistence layer is built on JPA entities and automatically maps domain objects to relational tables.
- The security chain is extensible; additional filters or authorization rules can be added in `SecurityConfig`.
- Validation is applied via Jakarta Validation annotations and enforced with `@Valid` in controller methods.
- The same architecture can be reused for different domains by swapping domain entities and service logic.
