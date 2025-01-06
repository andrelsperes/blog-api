# Blog API

The **Blog API** is a RESTful API built with **Spring Boot** to manage blog posts and their comments. This project demonstrates best practices such as using DTOs, separating layers (Controller, Service, and Repository), and automatic documentation with Swagger.

---

## **Table of Contents**

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [API Documentation](#api-documentation)
- [Tests](#tests)
- [CI/CD Configuration](#cicd-configuration)


---

## **Features**

- **Blog Post Management:**
    - Create, list, and retrieve blog posts by ID.
- **Comment Management:**
    - Add comments to specific blog posts.
- **Automated Documentation:**
    - Interactive API documentation with Swagger.
- **Automated Testing:**
    - Unit tests for controllers and services using MockMvc and Mockito.

---

## **Technologies Used**

- **Java 21**
- **Spring Boot 3.4.1**
    - Spring Web
    - Spring Data JPA
- **H2 Database** (In-memory database for development)
- **Swagger/OpenAPI** (For API documentation)
- **JUnit 5 and Mockito** (For unit testing)
- **GitHub Actions** (For CI/CD)

---

## **Requirements**

Ensure you have the following installed:
- **JDK 21+**
- **Maven 3.8+**
- **Git**

---

## **Installation and Setup**

1. Clone the repository:
   ```bash
   git clone https://github.com/andrelsperes/blog-api.git
   cd blog-api

2. Build the project:
    ```bash
    mvn clean install
   
3. Run the application:
    ```bash
    mvn spring-boot:run

4. Access the application at:
    ```bash
    http://localhost:8080
   
## **API Documentation**

The API is documented with Swagger and is accessible at:
```bash
http://localhost:8080/swagger-ui.html
```

### **Available Endpoints**

#### **Blog Posts**
- **GET** `/api/v1/posts` - List all blog posts (includes comment count).
- **POST** `/api/v1/posts` - Create a new blog post.
- **GET** `/api/v1/posts/{id}` - Retrieve a specific blog post by its ID.

#### **Comments**
- **POST** `/api/v1/posts/{id}/comments` - Add a comment to a specific blog post.

---

## **Tests**

Run the tests with:
```bash
mvn test
```

## **CI/CD Configuration**

This project uses GitHub Actions for continuous integration and continuous deployment (CI/CD). The configuration file is located at `.github/workflows/build.yml`.

### **GitHub Actions Configuration**

The GitHub Actions workflow is triggered on every push to any branch. It performs the following steps:

1. **Checkout repository**: Checks out the code from the repository.
2. **Set up JDK 21**: Sets up the Java Development Kit (JDK) version 21.
3. **Cache Maven dependencies**: Caches the Maven dependencies to speed up the build process.
4. **Build with Maven**: Runs the Maven build to compile and test the project.

Here is the content of the `.github/workflows/build.yml` file:

```yaml
name: Build Project

on:
  push:
    branches:
      - '**'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'

      - name: Cache Maven dependencies
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-m2-

      - name: Build with Maven
        run: mvn clean install