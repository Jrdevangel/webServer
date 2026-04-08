# Backend Module

Spring Boot backend module implementing user management with JWT-based authentication and secure session handling.

## 🚀 Evolution

The backend has evolved from a simple persistence-based architecture to a secure authentication system including:

- Stateless JWT-based authentication
- Persistent refresh token management
- Secure refresh token rotation strategy

## 🧩 Module Structure

com.example.webserver

├── config
├── controller
│ └── AuthController.java
├── dto
│ ├── LoginRequest.java
│ ├── RegisterRequest.java
│ ├── RefreshTokenRequest.java
│ └── AuthResponse.java
├── entity
│ └── RefreshToken.java
├── repository
│ ├── UserRepository.java
│ ├── JpaUserRepository.java
│ ├── RefreshTokenRepository.java
│ └── memory/InMemoryUserRepository.java
├── security
│ ├── JwtService.java
│ └── JwtAuthenticationFilter.java
├── service
│ ├── UserService.java
│ └── RefreshTokenService.java
└── resources

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

This prevents replay attacks and improves session security.

### 🔄 Auth Endpoints

| Method | Endpoint              | Description |
|--------|----------------------|-------------|
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Authenticate user |
| POST   | /api/auth/refresh    | Refresh access token |
| POST   | /api/auth/logout     | Logout (token invalidation planned) |

## 🧠 Security Layer

### JwtAuthenticationFilter

- Intercepts incoming requests
- Extracts JWT from Authorization header
- Validates token
- Injects authenticated user into SecurityContext

Public endpoints excluded from filtering:

- /api/auth/login
- /api/auth/register
- /api/auth/refresh
- /api/auth/logout

## 🗄 Database Configuration

When running under the `db` profile:

- PostgreSQL is used
- Spring Data JPA is active
- Docker configuration is externalized using environment variables (`.env`)

`application.yml` contains environment-specific configuration.

## 🐳 Docker Setup

```bash
docker compose up -d
```

This starts:

- PostgreSQL 16
- Persistent volume storage
- Port 5432 exposed locally

## 🧠 Service Layer

### UserService
- Handles user persistence
- Encodes passwords using BCrypt

### RefreshTokenService
- Creates refresh tokens
- Validates expiration
- Deletes tokens
- Implements refresh token rotation

---

## 📈 Architectural Maturity

The backend currently includes:

- Clean architecture (Controller → Service → Repository)
- Profile-based persistence strategy
- JWT authentication
- Stateful refresh token management
- Refresh token rotation
- Secure password handling (BCrypt)
- Dockerized PostgreSQL