```markdown
# Backend Module

Spring Boot backend module implementing user management with profile-driven persistence.

---

# 🧩 Module Structure

```
com.example.webserver
├── config
├── controller
├── repository
│   ├── UserRepository.java
│   ├── JpaUserRepository.java
│   └── memory/InMemoryUserRepository.java
├── service
└── resources
```

---

# 🎯 Design Principles

## 1. Repository Abstraction

`UserRepository` defines the persistence contract.

Business logic depends on the interface, not on a specific implementation.

## 2. Profile-Based Injection

Spring's `@Profile` annotation determines which repository implementation is injected:

* `dev` → `InMemoryUserRepository`
* `db`  → `JpaUserRepository`

This avoids conditional logic in services and keeps the architecture clean.

---

# 🗄 Database Configuration

When running under the `db` profile:

* PostgreSQL is used
* Spring Data JPA is active
* Docker configuration is externalized using environment variables (`.env`).

`application.yml` contains environment-specific configuration.

---

## 🐳 Docker Setup

The project includes a `docker-compose.yml` file to start PostgreSQL:

```bash
docker compose up -d
```

This starts:

* PostgreSQL 16

* Persistent volume storage

* Port 5432 exposed locally

---

# 🧠 Service Layer

`UserService` contains business logic and interacts only with `UserRepository`.

This ensures:

* Loose coupling

* Easier testing

* Future DB portability

---

# 🧪 Execution

### Development

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### With Database

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=db
```

Make sure Docker PostgreSQL is running before starting the `db` profile.

---

# 📈 Architectural Maturity

The backend now supports:

* Clean separation of concerns

* Multiple persistence strategies

* Dockerized PostgreSQL

* Profile-based environment configuration

* JPA integration

The module is ready for production-grade relational databases.
```