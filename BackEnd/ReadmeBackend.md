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

* H2 database is enabled
* Spring Data JPA is active
* Data initialization runs conditionally

`application.yml` contains environment-specific configuration.

---

# 🧠 Service Layer

`UserService` contains business logic and interacts only with `UserRepository`.

This ensures:

* Loose coupling
* Easier testing
* Future DB portability

---

# 🔒 Security & Initialization

* Legacy configuration classes removed
* `DataInitializer` runs only when required profile is active
* Application entry point simplified

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

---

# 📈 Architectural Maturity

The backend now supports:

* Clean separation of concerns
* Multiple persistence strategies
* Profile-based environment configuration
* JPA integration

The module is prepared for migration to PostgreSQL or any production-grade relational database.
