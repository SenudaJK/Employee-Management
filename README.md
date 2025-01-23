# Employee Management System

This is an Employee Management System built with Java, Spring Boot, and MySQL. The application allows you to manage employee records and provides a RESTful API for CRUD operations.

## Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose

## Project Structure

- `employee/src/main/java/com/organization/employee/exception/GlobalExceptionHandler.java`: Handles global exceptions.
- `employee/docker-compose.yml`: Docker Compose configuration for the application.
- `employee/wait-for-it.sh`: Script to wait for MySQL to be ready before starting the application.
- `employee/Dockerfile`: Dockerfile to build the application image.

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SenudaJK/employee-management-system.git
   cd employee-management-system
