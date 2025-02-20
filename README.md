# Employee Management System

A Spring Boot application with H2 database for development and PostgreSQL for production.

## Features
- CRUD operations for Employees.
- In-memory database (H2) for development.
- PostgreSQL for production (Dockerized).
- Swagger API documentation.

## Setup Instructions

### Local Development (H2 Database)
```sh
mvn clean install
mvn spring-boot:run
