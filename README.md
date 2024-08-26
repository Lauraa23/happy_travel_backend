# Happy Travel - Backend

Welcome to the backend repository for **Happy Travel**! This project provides the backend services for the Happy Travel application, including the REST API for managing travel destinations and user authentication.

## 📑 Table of Contents

1. [Overview](#-overview)
2. [Technologies](#-technologies)
3. [Getting Started](#-getting-started)
4. [Project Structure](#-project-structure)
5. [Features](#-features)
6. [API Endpoints](#-api-endpoints)
7. [Contributing](#-contributing)
8. [Team](#-team)

## 🧭 Overview

This repository contains the backend code for the Happy Travel application, developed with [Spring Boot](https://spring.io/projects/spring-boot) and [PostgreSQL](https://www.postgresql.org/). It provides the necessary APIs for user management and destination data handling.

## 🛠️ Technologies

- **[Spring Boot](https://spring.io/projects/spring-boot)**: A Java-based framework used for creating stand-alone, production-grade Spring-based applications.
- **[PostgreSQL](https://www.postgresql.org/)**: An open-source relational database management system.
- **[Java](https://www.oracle.com/java/)**: The programming language used for backend development.
- **[Maven](https://maven.apache.org/)**: A build automation tool used for managing project dependencies.

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK** (v17 or higher)
- **Maven** (v3.8 or higher)
- **PostgreSQL** (v12 or higher)

### Installation

To set up the project locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Lauraa23/happy_travel_backend.git

2. **Navigate to the project directory**:
   ```bash
   cd happy-travel-backend

3. **Configure the database**:
    Create a PostgreSQL database and update the "application.propierties" file with your database credentials.

4. **Install dependencies**:
   ```bash
   mvn install

5. **Run the application**:
   ```bash
   mvn spring-boot:run

## 📚 Project Structure

Here's a brief overview of the project structure:

- `src/`: Contains all the source code for the application.
  - `main/java/`: Java source code for the application.
    - `com/happytravel/`: Base package for the application.
      - `config/`: Configuration classes for Spring Boot.
      - `controllers/`: REST controllers handling API requests.
      - `jwt/`: Contains JWT (JSON Web Token) utilities and configurations for handling authentication and authorization.
        - `JwtTokenProvider.java`: Utility class for creating and validating JWT tokens.
        - `JwtAuthenticationFilter.java`: Filter for validating JWT tokens in incoming requests.
        - `JwtAuthenticationEntryPoint.java`: Handles authentication errors.
      - `models/`: Entities and data models.
      - `repositories/`: Interfaces for database access.
      - `services/`: Business logic and service layer.
  - `main/resources/`: Configuration files and static resources.
    - `application.properties`: Application configuration, including database settings.

## 📄 Features

- **User Management**: REST API endpoints for user registration, authentication, and profile management.
- **CRUD Operations**: Endpoints for creating, reading, updating, and deleting travel destinations.
- **Database Integration**: Uses PostgreSQL for data storage and management.
- **Authentication**: Basic authentication and authorization for secured endpoints.

## 🌐 API Endpoints

Here's a brief overview of the available API endpoints:

- **User Endpoints**:
  - `POST /api/users/register`: Register a new user.
  - `POST /api/users/login`: Authenticate a user and get a token.
  - `GET /api/users/{id}`: Retrieve user profile.

- **Destination Endpoints**:
  - `POST /api/destinations`: Create a new travel destination.
  - `GET /api/destinations`: Retrieve all travel destinations.
  - `GET /api/destinations/{id}`: Retrieve a specific destination.
  - `PUT /api/destinations/{id}`: Update a specific destination.
  - `DELETE /api/destinations/{id}`: Delete a specific destination.

## 🤝 Contributing

We welcome contributions! Feel free to fork the project and submit pull requests, or open issues if you find bugs or have suggestions.

## 🧑‍🤝‍🧑 Team

This project was developed by:

- [Rebeca Garcia](https://github.com/rebkg87) - **Scrum Master**
- [Isa Afonso](https://github.com/IsaLagu) - **Product Owner**
- [Mercedes Celedón](https://github.com/Mercedes-Celedon)
- [Laura del Pino](https://github.com/Lauraa23)
- [Carol Alonso](https://github.com/Calonsogon)

Feel free to reach out if you have any questions or need further information about the project.


