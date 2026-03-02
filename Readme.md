# WebServer Project

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Profile--Based-orange)
![Status](https://img.shields.io/badge/Status-Active-success)

---

Backend service built with Spring Boot following a clean repository abstraction and profile-based persistence strategy.

## 🚀 Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL (Docker)
* H2 (optional local testing)
* Maven
* Docker
* Caddy

---

# 🏗 Architecture Overview

The project follows a layered architecture:
Controller → Service → Repository (Interface) → Implementation

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
* Backed by PostgreSQL (via Docker)
* Enables full JPA persistence
* Suitable for production-like environments

Profiles are activated via:

```bash
-Dspring.profiles.active=dev

or

-Dspring.profiles.active=db


📦 Repository Implementations

#UserRepository

Domain-level abstraction that defines the persistence contract.

#InMemoryUserRepository

* Activated with @Profile("dev")
* Stores users in memory
* Provides lightweight authentication support

#JpaUserRepository

* Activated with @Profile("db")
* Extends Spring Data JPA
* Persists users in PostgreSQL

🧪 Running the Application

Development Mode
mvn spring-boot:run -Dspring-boot.run.profiles=dev
Database Mode
mvn spring-boot:run -Dspring-boot.run.profiles=db

--- 

🐳 Docker & Caddy Setup

1. Create a .env file in the root

POSTGRES_PASSWORD=your_secure_password
.env is ignored by Git. Use .env.example to share required environment variables without exposing secrets.

2. Start the full stack

docker compose up --build

This will launch:

* PostgreSQL database

* Spring Boot backend
* Nginx frontend
* Caddy reverse proxy

3. Stop and remove containers and volumes

docker compose down -v

📌 Current Status

* Clean repository abstraction implemented
* Profile-based persistence fully functional
* Dockerized infrastructure with PostgreSQL, backend, frontend, and Caddy
* Ready for production deployment

---

🔜 Next Improvements

* CI/CD pipeline

* Unit and integration test coverage
* Production-ready configuration and monitoring

This project demonstrates backend architecture evolution from basic configuration to structured, profile-driven persistence design with a fully Dockerized stack.