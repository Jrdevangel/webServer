# WebServer Project

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Profile--Based-orange)
![Status](https://img.shields.io/badge/Status-Active-success)

---

Backend-oriented web infrastructure built with Spring Boot using JWT authentication, refresh token rotation, profile-based persistence, and a fully Dockerized environment.

---

## 🚀 Tech Stack

* Java 21
* Spring Boot 3
* Spring Security
* JWT Authentication
* Refresh Token Rotation
* Spring Data JPA
* PostgreSQL
* Maven
* Docker
* Caddy
* Nginx

---

# 🏗 Architecture Overview

The project follows a layered architecture:

Controller → Service → Repository → Database

Main backend components:

* Stateless JWT authentication
* Refresh token persistence and rotation
* Spring Security filter chain
* Profile-based repository strategy
* Dockerized infrastructure
* Reverse proxy with Caddy

---

# 🖼 Architecture Diagram

![WebServer Architecture](./a_flowchart_digital_illustration_of_a_docker_based.png)

Docker stack components:

* **PostgreSQL** → database container
* **Spring Boot Backend** → REST API + authentication
* **Nginx Frontend** → static frontend serving
* **Caddy** → reverse proxy handling HTTP/HTTPS
* Shared Docker bridge network between services

---

# 🔐 Authentication System

Implemented authentication features:

* User registration
* User login
* JWT access token generation
* Refresh token generation
* Refresh token rotation
* Stateless authentication
* Spring Security integration
* BCrypt password encryption

Main security components:

* `JwtService`
* `JwtAuthenticationFilter`
* `SecurityConfig`
* `CustomUserDetailsService`
* `AuthService`

---

# 🔄 Profile-Based Repository Strategy

Two persistence strategies are available:

## `dev` profile

* Uses in-memory persistence
* No external database required
* Faster startup for development

## `db` profile

* Uses PostgreSQL persistence
* Full JPA integration
* Production-oriented environment

Activate profile:

```bash
-Dspring.profiles.active=dev

or

-Dspring.profiles.active=db

```
---

# 📦 Repository Layer

# UserRepository

Domain abstraction defining persistence operations.

# InMemoryUserRepository

* Activated with @Profile("dev")
* Stores users in memory
* Provides lightweight authentication support

# JpaUserRepository

* Activated with @Profile("db")
* Extends Spring Data JPA
* Persists users in PostgreSQL

---

# 🧪 Running the Application

# Development Mode

```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

# Database Mode
```
mvn spring-boot:run -Dspring-boot.run.profiles=db
````

--- 

# 🐳 Docker & Caddy Setup

# 1. Create a .env file

```
POSTGRES_PASSWORD=your_secure_password
JWT_SECRET=your_base64_secret
```

```.env``` is ignored by Git security reasons.

---

# 2. Start the full stack

```docker compose up --build```

Services started:
* PostgreSQL
* Spring Boot backend
* Nginx frontend
* Caddy reverse proxy

---

# 3. Stop and remove containers and volumes

```docker compose down -v````

---

# 📌 Current Status

* JWT authentication implemented
* Refresh token rotation implemented
* Stateless Spring Security configured
* Dockerized PostgreSQL environment
* Environment variable secret management
* Backend and frontend connected through Docker network
* Reverse proxy configured with Caddy
* Production-oriented backend structure established

---

# 🔜 Next Improvements

Role-based authorization
HTTPS production deployment
CI/CD pipeline
Unit and integration testing
Monitoring and observability
API documentation with Swagger/OpenAPI

---

This project demonstrates the evolution from a basic Spring Boot backend into a more production-oriented backend infrastructure with secure authentication, Docker orchestration, and scalable architecture principles.