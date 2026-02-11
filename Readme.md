# WebServer Project

Backend service built with Spring Boot following a clean repository abstraction and profile-based persistence strategy.

## 🚀 Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* H2 (dev/db testing)
* Maven

---

# 🏗 Architecture Overview

The project follows a layered architecture:

```
Controller → Service → Repository (Interface) → Implementation
```

The persistence layer is abstracted through a domain-level `UserRepository` contract, allowing multiple implementations depending on the active Spring profile.

---

# 🔄 Profile-Based Repository Strategy

Two persistence strategies are available:

## `dev` profile

* Uses `InMemoryUserRepository`
* No database required
* Fast startup
* Ideal for development and testing

## `db` profile

* Uses `JpaUserRepository`
* Backed by H2 database
* Enables full JPA persistence
* Suitable for integration testing and future production DB integration

Profiles are activated via:

```bash
-Dspring.profiles.active=dev
```

or

```bash
-Dspring.profiles.active=db
```

---

# 📦 Repository Implementations

## `UserRepository`

Domain-level abstraction that defines the persistence contract.

## `InMemoryUserRepository`

* Activated with `@Profile("dev")`
* Stores users in memory
* Provides lightweight authentication support

## `JpaUserRepository`

* Activated with `@Profile("db")`
* Extends Spring Data JPA
* Persists users in H2 database

---

# 🧪 Running the Application

## Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Database Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=db
```

---

# 📌 Current Status

* Clean repository abstraction implemented
* Profile-based persistence fully functional
* Legacy configuration removed
* Ready for PostgreSQL or other production database integration

---

# 🔜 Next Improvements

* PostgreSQL integration
* Docker containerization
* CI/CD pipeline
* Unit and integration test coverage

---

This project demonstrates backend architecture evolution from basic configuration to structured, profile-driven persistence design.
