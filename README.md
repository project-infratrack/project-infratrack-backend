# Spring Boot Backend Build and Run Instructions

This document provides instructions for setting up, building, and running the Spring Boot backend application.

## Prerequisites

Before building and running the Spring Boot backend, ensure you have the following prerequisites:

1.  **Git:** Required for cloning the repository and managing code.
2.  **Java Development Kit (JDK):** Java 21 or a compatible version is required for building the Spring Boot application.
3.  **Maven:** Maven is used to build and manage the Spring Boot project.

## Setup

1.  **Clone the Repository:**

    ```bash
    git clone <repository_url>
    cd <repository_directory>
    ```

    Replace `<repository_url>` with the URL of your repository and `<repository_directory>` with the name of the directory you want to clone into.

2.  **Install JDK and Maven (if not already installed):**

    * Download and install Java 21 (or a compatible version).
    * Download and install Maven.
    * Set the `JAVA_HOME` environment variable to the JDK installation directory.
    * Add the JDK's `bin` and Maven's `bin` directories to your system's `PATH` environment variable.

## Running the Application (Locally)

To run the Spring Boot application locally:

1.  **Navigate to the Backend Directory:**

    ```bash
    cd infratrack_userportal-backend
    ```

2.  **Build the Project:**

    ```bash
    mvn clean install
    ```

3.  **Run the Application:**

    ```bash
    java -jar target/*.jar
    ```

    Or, if you use the Spring Boot Maven plugin:

    ```bash
    mvn spring-boot:run
    ```

    The application will start, and you can access it based on its configuration (e.g., `http://localhost:8080`).

## Error Handling

* **`mvn clean install` errors:**
    * Verify your JDK and Maven installations.
    * Check your internet connection if dependencies are not downloading.
    * Review the Maven output for specific error messages.
    * Ensure the `pom.xml` file is correctly configured.
* **`java -jar` or `mvn spring-boot:run` errors:**
    * Check the application logs for runtime exceptions.
    * Verify the application's configuration (e.g., database connections, ports).
    * Ensure the required environment variables are set.
