# Backend Module

Spring Boot backend module implementing secure authentication, authorization, JWT session management, and role-based access control (RBAC).

## 🚀 Current Features

The backend currently includes:

* Stateless JWT-based authentication
* Persistent refresh token management
* Secure refresh token rotation strategy
* Logout endpoint with refresh token revocation
* Token replay/reuse protection
* Centralized HTTP exception handling
* DTO validation using Jakarta Validation
* Role-Based Access Control (RBAC)
* Route-level authorization with Spring Security
* Method-level authorization using `@PreAuthorize`
* Protected Swagger/OpenAPI access
* Refresh token persistence with PostgreSQL
* Manual API verification using Postman

---

## 🧩 Module Structure

```text
com.example.webserver

├── config
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── UserController.java
│   └── AdminController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── RefreshTokenRequest.java
│   ├── LogoutRequest.java
│   ├── AuthResponse.java
│   └── UserProfileResponse.java
├── entity
│   ├── UserEntity.java
│   ├── RefreshToken.java
│   └── Role.java
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── InvalidCredentialsException.java
│   ├── UserAlreadyExistException.java
│   ├── UserNotFoundException.java
│   ├── TokenExpiredException.java
│   └── TokenReuseException.java
├── repository
│   ├── UserRepository.java
│   └── RefreshTokenRepository.java
├── security
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
├── service
│   ├── AuthService.java
│   ├── UserService.java
│   └── RefreshTokenService.java
└── resources
```

---

## 🔐 Authentication & Authorization

### JWT-Based Authentication

The system uses stateless authentication via JWT:

* Access Tokens (short-lived)
* Refresh Tokens (persistent and stored in database)

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

### Refresh Token Strategy

* Refresh tokens are persisted in PostgreSQL
* Each refresh token has an expiration date
* Tokens are rotated on every refresh request
* Old refresh tokens are revoked
* Logout invalidates refresh tokens
* Reused, expired, or revoked refresh tokens trigger security exceptions

This design mitigates replay attacks and improves session security.

---

## 🛡 Role-Based Access Control (RBAC)

The backend implements RBAC using Spring Security authorities.

### Available Roles

```text
USER
ADMIN
```

### Authority Mapping

Roles are automatically mapped to Spring Security authorities:

```text
USER  → ROLE_USER
ADMIN → ROLE_ADMIN
```

via:

```java
SimpleGrantedAuthority(
    "ROLE_" + user.getRole().name()
)
```

### Route-Level Authorization

Configured in:

```text
SecurityConfig.java
```

Protected routes:

```text
/api/user/**   → USER or ADMIN
/api/admin/**  → ADMIN only
```

### Method-Level Authorization

Method security is enabled using:

```java
@EnableMethodSecurity
```

Example:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

## 🔄 Authentication Endpoints

| Method | Endpoint             | Description          |
| ------ | -------------------- | -------------------- |
| POST   | `/api/auth/register` | Register new user    |
| POST   | `/api/auth/login`    | Authenticate user    |
| POST   | `/api/auth/refresh`  | Refresh access token |
| POST   | `/api/auth/logout`   | Revoke refresh token |

---

## 🧠 Security Layer

### JwtAuthenticationFilter

The request authentication layer:

* Intercepts incoming requests
* Extracts JWT from Authorization header
* Validates JWT signature and expiration
* Loads authenticated user
* Injects user into Spring Security `SecurityContext`

Authorization format:

```http
Authorization: Bearer <access_token>
```

Public endpoints:

```text
/api/auth/register
/api/auth/login
/api/auth/refresh
/swagger-ui/**
/v3/api-docs/**
```

All remaining endpoints require authentication.

---

## 🗄 Database Configuration

The backend uses PostgreSQL with Spring Data JPA.

Current setup:

* PostgreSQL database
* Hibernate schema synchronization (`ddl-auto=update`)
* Automatic entity-to-table mapping
* Environment variable support for credentials

Managed via:

```text
application.yml
```

---

## 🐳 Docker Setup

Start infrastructure:

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

Do not run the Dockerized backend and local Spring Boot simultaneously.

Both use:

```text
Port 8080
```

Running both causes:

```text
Port 8080 was already in use
```

---

## ✅ API Verification

The backend has been manually verified using Postman.

Validated flows:

* Register
* Login
* JWT authentication
* Refresh token persistence
* Refresh token rotation
* Logout token revocation
* RBAC (`USER` vs `ADMIN`)
* Protected endpoints
* JWT authorization via Bearer token

---

## 📈 Architectural Maturity

Current backend maturity includes:

* Clean layered architecture (Controller → Service → Repository)
* JWT authentication
* Refresh token persistence
* Refresh token rotation
* Logout + token revocation
* Replay/reuse attack protection
* BCrypt password hashing
* DTO validation
* Global exception handling
* RBAC authorization
* Route-level security
* Method-level authorization
* Protected Swagger/OpenAPI
* PostgreSQL persistence
* Dockerized infrastructure
* Postman verification workflow