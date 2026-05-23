# Backend Module

Spring Boot backend module implementing user management with JWT-based authentication and secure session handling.

## 🚀 Current Features

The backend currently includes:

- Stateless JWT-based authentication
- Persistent refresh token management
- Secure refresh token rotation strategy
- Centralized exception handling
- Service-layer authentication (AuthService)
- DTO validation using Jakarta Validation
- Logout endpoint with refresh token revocation
- Token replay/reuse protection
- Refresh token persistence with PostgreSQL
- Manual API verification using Postman

## 🧩 Module Structure

com.example.webserver

├── config
├── controller
│   └── AuthController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── RefreshTokenRequest.java
│   ├── LogoutRequest.java
│   └── AuthResponse.java
├── entity
│   ├── UserEntity.java
│   └── RefreshToken.java
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── TokenExpiredException.java
│   └── TokenReuseException.java
├── repository
│   ├── UserRepository.java
│   ├── JpaUserRepository.java
│   ├── RefreshTokenRepository.java
│   └── memory/InMemoryUserRepository.java
├── security
│   ├── JwtService.java
│   └── JwtAuthenticationFilter.java
├── service
│   ├── AuthService.java
│   ├── UserService.java
│   └── RefreshTokenService.java
└── resources

---

## 🔐 Authentication & Security

### JWT-Based Authentication

The system uses stateless authentication via JWT:

- Access tokens (short-lived)
- Refresh tokens (persistent, stored in DB)

### Authentication Flow

```text
Register / Login
        ↓
Access Token + Refresh Token
        │
        ├── Access Token expires
        │          ↓
        │   POST /api/auth/refresh
        │          ↓
        │   New Access Token + New Refresh Token
        │
        └── POST /api/auth/logout
                   ↓
            Refresh Token revoked
                   ↓
              Future refresh blocked (403)
```

---

### Token Lifecycle

```text
Client
  │
  ├── POST /api/auth/login
  │        ↓
  │   Access Token + Refresh Token
  │
  ├── Access Token expires
  │
  ├── POST /api/auth/refresh
  │        ↓
  │   New Access Token + New Refresh Token
  │
  └── POST /api/auth/logout
           ↓
      Refresh Token revoked
           ↓
      Reused or revoked refresh token rejected (403 Forbidden)
```

---

### Refresh Token Strategy

- Refresh tokens are stored in database
- Each token has an expiration date
- Tokens are **rotated on use**:
  - Old token is deleted
  - New token is issued
- Tokens can be revoked on logout
- Expired, reused, or revoked tokens trigger security exceptions

This prevents replay attacks and improves session security.

### 🔄 Auth Endpoints

| Method | Endpoint              | Description |
|--------|----------------------|-------------|
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Authenticate user |
| POST   | /api/auth/refresh    | Refresh access token |
| POST   | /api/auth/logout     | Revoke refresh token |

---

## 🧠 Security Layer

### JwtAuthenticationFilter (Request Authentication Layer)

* Intercepts incoming requests
* Extracts JWT from Authorization header:

```http
Authorization: Bearer <access_token>
```
* Validates token
* Injects authenticated user into SecurityContext

Public endpoints:
* /api/auth/login
* /api/auth/register
* /api/auth/refresh
* /api/auth/logout

---

## 🗄 Database Configuration

The backend uses PostgreSQL with Spring Data JPA.

Current setup:

* PostgreSQL database
* Hibernate automatic schema updates (`ddl-auto=update`)
* Automatic table creation and synchronization
* Environment variable support for credentials

Configuration is managed through:

`application.yml`

--- 

## 🐳 Docker Setup

Start the infrastructure:

```bash
docker compose up -d
```

Services:

* PostgreSQL database
* Spring Boot backend
* Frontend application
* Caddy reverse proxy

---

## ⚠️ Local Development Warning

Do not run the Dockerized backend and the local Spring Boot instance simultaneously.

The backend container and:

```bash
mvn spring-boot:run
```

both use port `8080`.

If both are running, Spring Boot will fail with:

```text
Port 8080 was already in use
```

---

## ✅ API Verification

The authentication flow has been manually verified using Postman.

Validated endpoints:

* Register (`/api/auth/register`)
* Login (`/api/auth/login`)
* Refresh token persistence and validation in PostgreSQL
* Refresh token generation and rotation
* Logout token revocation
* JWT issuance and validation

---

## 📈 Architectural Maturity
* Clean architecture (Controller → Service → Repository)
* Separation of concerns (AuthService introduced)
* JWT authentication
* Secure password handling (BCrypt)
* Stateful refresh token management
* Refresh token rotation
* Logout + token revocation
* Token replay/reuse protection
* DTO validation (Jakarta Validation)
* Centralized exception handling
* Dockerized infrastructure
* Postman integration for automated testing