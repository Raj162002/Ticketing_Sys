# Spring Backend Project

## Overview

This is a Spring Boot-based backend application designed to manage vendors, customers, events, and tickets. It provides RESTful APIs for interacting with the system and integrates with a PostgreSQL database to store and manage data. The project also supports vendor dashboards, event creation, ticket management, and other core functionalities.

## Setup Instructions

1. **Clone the Repository**
    - Clone this repository to your local machine using:
      ```bash
      git clone <repository-url>
      cd <project-directory>
      ```

2. **Database Configuration**
    - Open the `src/main/resources/application.properties` file.
    - Update the following fields with your PostgreSQL database credentials:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>
      spring.datasource.username=<your-username>
      spring.datasource.password=<your-password>
      spring.jpa.hibernate.ddl-auto=update
      ```
    - Replace `<database-name>`, `<your-username>`, and `<your-password>` with the appropriate values.

3. **Install PostgreSQL**
    - Make sure PostgreSQL is installed on your system.
    - Create a new database for the project:
      ```sql
      CREATE DATABASE <database-name>;
      ```

4. **Dependencies**
    - Ensure you have Maven installed on your system. Install all project dependencies by running:
      ```bash
      mvn clean install
      ```

## Build and Run

1. **Build the Project**
    - Run the following command to compile and package the application:
      ```bash
      mvn package
      ```

2. **Run the Application**
    - Start the application using:
      ```bash
      java -jar target/<application-name>.jar
      ```
    - Replace `<application-name>` with the name of the generated `.jar` file.

3. **Access the APIs**
    - By default, the application runs on `http://localhost:8080`.
    - Test the APIs using tools like Postman or Swagger (if integrated).

## Configuration and Execution

1. **Modify Properties**
    - To change server settings like port, open the `application.properties` file:
      ```properties
      server.port=8080
      ```
    - Change the port number if required.

2. **Logging**
    - Configure logging levels in `application.properties`:
      ```properties
      logging.level.root=INFO
      logging.level.org.springframework.web=DEBUG
      ```

3. **API Documentation**
    - If Swagger is integrated, access the API documentation at:
      ```text
      http://localhost:8080/swagger-ui.html
      ```

4. **Running with an IDE**
    - Open the project in an IDE like IntelliJ IDEA or Eclipse.
    - Run the `SpringBootApplication` class from the IDE to start the application.

## Additional Notes

- Ensure your PostgreSQL server is running before starting the application.
- The application uses Java 17. Ensure the correct version is installed.
- For testing, you can populate the database using pre-defined scripts or directly via API calls.

Feel free to reach out for additional support!

