# FamilyHelpUAETM — CSC408 Programming Assignment 2

## Overview
FamilyHelpUAETM is a secure RESTful community support platform where families can:
- Register and manage family profiles
- Offer and request help (childcare, tutoring, transport, elder support, household help)
- Accept/reject requests and track completed support tasks
- Exchange feedback and build trust/reputation

The system is implemented with Spring Boot and secured using JWT Bearer authentication.

## Assignment Alignment
This project is designed to satisfy CSC408 requirements:
- RESTful API design and stateless architecture
- Secure authentication/authorization
- Trust and reputation mechanisms
- Concurrency-safe interactions
- Scalability-aware architecture
- Swagger/OpenAPI documentation
- Testing and demo readiness

## Tech Scope
- Backend: Spring Boot REST API
- Security: Spring Security + JWT Bearer tokens
- Data: Relational or NoSQL persistence
- Docs: Swagger/OpenAPI

## Required Java File Header (Submission Rule)
Every Java file should include:
- Section number
- Group number
- Student IDs and names

Example:
```java
// Section: <section>
// Group: <group-number>
// Students: <id1 - Name1>, <id2 - Name2>, <id3 - Name3>
```

## Core API Endpoints

### Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`

### Family Profiles
- `GET /api/families/{id}`
- `PUT /api/families/{id}`

### Help Offers & Requests
- `POST /api/offers`
- `GET /api/offers`
- `POST /api/requests`
- `POST /api/requests/{id}/accept`
- `POST /api/requests/{id}/reject`
- `POST /api/requests/{id}/complete`

### Feedback & Reputation
- `POST /api/feedback`
- `GET /api/families/{id}/reputation`

### Interaction History
- `GET /api/families/{id}/history`

## Authentication & Authorization (JWT Bearer)
- Use header: `Authorization: Bearer <accessToken>`
- Only authenticated families can access protected endpoints
- Authorization checks must enforce ownership/role constraints
- Expired/invalid tokens should return `401 Unauthorized`

## Minimal Environment Configuration
Use only JWT and DB-related configuration.

```env
JWT_SECRET=JWT_SECRET_PLACEHOLDER
JWT_EXPIRATION_MS=3600000
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/familyhelp
SPRING_DATASOURCE_USERNAME=db_user
SPRING_DATASOURCE_PASSWORD=db_password_placeholder
```

## Security Requirements
- Password hashing (bcrypt/argon2)
- Input validation and sanitization
- Protection against unauthorized access
- Secure handling of sensitive data
- HTTPS in deployment environments

## Concurrency & Scalability Notes
- Handle concurrent updates safely (transactions, optimistic locking)
- Return conflict status (`409`) when needed
- Keep services stateless for horizontal scaling
- Optimize data access (indexing/caching where applicable)

## Swagger Documentation
Expose and maintain clear API docs including:
- Endpoint descriptions
- Request/response schemas
- JWT auth workflow
- Trust/reputation logic
- Usage examples

## Testing & Validation
Include:
- Unit tests for core services
- Integration tests for API flows
- Concurrency tests for simultaneous interactions
- Validation/security test cases

## Deliverables Checklist
- Full backend project source code
- Swagger/OpenAPI documentation
- Test cases with results
- Demo video
- Report covering architecture, scalability, concurrency, security, and self-evaluation

## Evaluation Focus (Rubric Summary)
- REST API implementation quality
- Frontend integration readiness
- Security + authorization + trust/reputation + scalability
- Testing and concurrent-user simulation
- Documentation and demo clarity
- Bonus for innovation
