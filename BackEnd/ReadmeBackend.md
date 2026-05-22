# Backend Module

Spring Boot backend module implementing user management with JWT-based authentication and secure session handling.

## 🚀 Evolution

The backend has evolved from a simple persistence-based architecture to a secure authentication system including:

- Stateless JWT-based authentication
- Persistent refresh token management
- Secure refresh token rotation strategy
- Centralized exception handling
- Service-layer authentication (AuthService)
- DTO validation using Jakarta Validation
- Logout endpoint with refresh token revocation
- Token replay/reuse protection

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

When running under the dev profile:
* PostgreSQL is used
* Spring Data JPA is active
* Tables are generated automatically

Configuration is managed via `application-dev.yml` and environment variables.

--- 

## 🐳 Docker Setup

```bash
docker compose up -d
```

Starts:
* PostgreSQL
* Persistent volume
* Port 5432 exposed locally

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
* Dockerized PostgreSQL
* Postman integration for automated testing