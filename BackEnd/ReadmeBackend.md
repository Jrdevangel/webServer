# 🚀 WebServer Backend

A secure and modular **Spring Boot backend** with authentication and an admin dashboard.  
Built step by step following **DevOps best practices**.  

---

## ✨ Features
- ⚡ Built with **Spring Boot 3** & **Java 17**
- 🔐 Authentication powered by **Spring Security**
- 🌐 RESTful API endpoints
- 🏗️ Modular and ready for expansion
- 🎯 Prepared for **frontend integration**
- 🗄️ Database integration planned (PostgreSQL/MySQL)
- 👥 Role-based authentication (ADMIN / USER)
- 🧾 Custom login with Thymeleaf
- 🛡️ Protected routes per role
- 🔀 Dynamic post-login redirection
- 🚪 Secure logout handling
- 🗃️ Ready for database-backed users

---

## 📂 Project Structure

webServer/
│
├── src/main/java/com/example/webserver
│   ├── WebServerApplication.java        # Main Spring Boot application
│   ├── controller/
│   │     ├── LoginController.java       # Login endpoint + Thymeleaf view
│   │     └── DashboardController.java   # Admin dashboard controller (ADMIN only)
│   │     └── ProfileController.java     # User profile controller (USER / ADMIN)
│   ├── config/
│   │     └── SecurityConfig.java        # Spring Security configuration
│   └── service/
│         └── DashboardService.java      # Service layer
│
├── src/main/resources
│   ├── templates/
│   │     └── login.html               # Thymeleaf login view
│   │     └── dashboard.html
│   │     └── user/
│              └── profile.html
│
├── pom.xml                             # Maven dependencies
└── README.md                           # Project documentation

---

## ⚙️ Requirements
- **Java 17**
- **Maven 3.9+**
- **Spring Boot 3.5.0**
- **Docker Desktop** (For the database)
- A terminal (MacOS)
