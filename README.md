# 🥔 Gamja Market Backend (감자마켓)

[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org/)
[![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)

A microservices-oriented **Multi-module (Monorepo)** project built with Kotlin and Spring Boot. This architecture ensures high reusability of domain logic while allowing independent deployment of each application module.

---

## Project Structure

The project is divided into two main layers: Apps (executable services) and Libs (reusable libraries).



### Apps (Executable Applications)
Independent Spring Boot applications that serve specific business purposes.
- **`market-api`**: Main service API for users (Port: `8080`)
- **`market-admin`**: Back-office management tool (Port: `8081`)
- **`market-batch`**: Scheduled tasks and bulk data processing

### Libs (Shared Libraries)
Core components shared across multiple `Apps`.
- **`common-domain`**: Database Entities, Repositories, and core domain services.
- **`common-dto`**: Shared Request/Response Data Transfer Objects.
- **`common-utils`**: Common utility functions (Dates, Strings, Security).

---

## Tech Stack
- **Language**: Kotlin 1.9.22
- **Framework**: Spring Boot 3.2.2
- **Build Tool**: Gradle 8.14 (Kotlin DSL)
- **JVM Strategy**: Java 17 (Enforced via **Gradle Toolchain**)
- **Monitoring**: Spring Boot Actuator

---

## Getting Started

### 1. Requirements
The project uses the **Gradle Toolchain** feature. Even if your local environment has a different Java version, Gradle will automatically use/download **JDK 17** for consistency.

### 2. Configuration (Essential) 
Before running the applications, you must create an `application.yml` file for each module. We provide sample files with the necessary structure.

1.  **Locate the samples:** Find `application.yml.sample` in the `src/main/resources` directory of each app module (`market-api`, `market-admin`, `market-batch`).
2.  **Copy and Rename:** Copy the sample file and rename it to `application.yml`.
    ```bash
    # Example for market-api
    cp apps/market-api/src/main/resources/application.yml.sample apps/market-api/src/main/resources/application.yml
    ```
3.  **Update Credentials:** Open the newly created `application.yml` and update the database credentials, API keys, and other environment-specific settings to match your local setup.

> **Note:** `application.yml` is included in `.gitignore` to prevent sensitive information (passwords, secret keys) from being leaked. **Never commit your actual `application.yml`!**

### 3. Build & Test
Build the entire project and run all tests (including health checks):
```bash
./gradlew clean build
```
### 3. Running Services
You can run each application module independently:
```
# Start the API service
./gradlew :apps:market-api:bootRun

# Start the Admin service
./gradlew :apps:market-admin:bootRun

# Start the Batch service
./gradlew :apps:market-Batch:bootRun
```

### 4. Verification (Health Check)
Check the status of running applications:

API Status: http://localhost:8080/actuator/health

Admin Status: http://localhost:8081/actuator/health

## Development Guide
### Adding a New Common Library
1. Create a directory in /libs.
2. Add the module to settings.gradle.kts.
3. Configure build.gradle.kts without the org.springframework.boot plugin (only use dependency-management).

### Java Version Policy
Java version is strictly managed in the root build.gradle.kts:
```
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
```