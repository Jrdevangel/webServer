# Backend Module

Spring Boot backend module implementing user management with JWT-based authentication and secure session handling.

## 🚀 Evolution

The backend has evolved from a simple persistence-based architecture to a secure authentication system including:

- Stateless JWT-based authentication
- Persistent refresh token management
- Secure refresh token rotation strategy
- Centralized exception handling
- Service-layer authentication (AuthService)

## 🧩 Module Structure

com.example.webserver

├── config
├── controller
│   └── AuthController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── RefreshTokenRequest.java
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

Login → Access Token + Refresh Token
↓
Access Token expires
↓
POST /api/auth/refresh
↓
New Access Token + New Refresh Token (rotation)

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
  └── POST /api/auth/refresh (with Refresh Token)
           ↓
     New Access Token + New Refresh Token (rotation)
```
---

### Refresh Token Strategy

- Refresh tokens are stored in database
- Each token has an expiration date
- Tokens are **rotated on use**:
  - Old token is deleted
  - New token is issued
- Invalid or replayed tokens trigger exceptions

This prevents replay attacks and improves session security.

### 🔄 Auth Endpoints

| Method | Endpoint              | Description |
|--------|----------------------|-------------|
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Authenticate user |
| POST   | /api/auth/refresh    | Refresh access token |

---

## 🧠 Security Layer

### JwtAuthenticationFilter (to be integrated next)

* Intercepts incoming requests
* Extracts JWT from Authorization header:

```http
Authorization: Bearer <access_token>
```
* Validates token
* Injects authenticated user into SecurityContext

Public endpoints excluded:
* /api/auth/login
* /api/auth/register
* /api/auth/refresh

---

## 🗄 Database Configuration

When running under the dev profile:
* PostgreSQL is used
* Spring Data JPA is active
* Tables are generated automatically

Configuration is managed via `application-dev.yml` and environment variables.

--- 

## 🐳 Docker Setup
```
docker compose up -d
````

Starts:
* PostgreSQL
* Persistent volume
* Port 5432 exposed locally

---

## 📈 Architectural Maturity

The backend currently includes:

* Clean architecture (Controller → Service → Repository)
* Separation of concerns (AuthService introduced)
* JWT authentication
* Stateful refresh token management
* Refresh token rotation
* Centralized exception handling
* Secure password handling (BCrypt)
* Dockerized PostgreSQL
* Postman integration for automated testing