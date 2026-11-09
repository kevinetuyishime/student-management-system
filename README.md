# Student Registration and Management System

## 1. Project Overview

The Student Registration and Management System is a web-based application developed using Spring Boot. It allows users to register students, view all registered students, and retrieve individual student records.

The application uses PostgreSQL to store student information and Thymeleaf to display the web pages.

## 2. Technologies Used

- Java 17
- Spring Boot 4.1.2
- Spring MVC
- Thymeleaf
- PostgreSQL
- JDBC (Java Database Connectivity)
- Maven
- HTML and CSS
- IntelliJ IDEA

## 3. Main Features

- Register new students.
- View all registered students.
- View an individual student using their ID.
- Validate student information.
- Require students to be at least 19 years old.
- Prevent duplicate email addresses.
- Store student records in PostgreSQL.

## 4. Project Structure

```text
studentmanagement/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/studentmanagement/
│       │       ├── controller/
│       │       │   └── StudentController.java
│       │       ├── model/
│       │       │   └── Student.java
│       │       ├── repository/
│       │       │   └── StudentRepository.java
│       │       ├── service/
│       │       │   └── StudentService.java
│       │       └── StudentmanagementApplication.java
│       │
│       └── resources/
│           ├── static/css/
│           │   └── style.css
│           ├── templates/
│           │   ├── register.html
│           │   └── students.html
│           └── application.properties
│
├── .env.example
├── .gitignore
├── pom.xml
└── README.md
```

## 5. Database Configuration

Database name: `student_management`

Create the database in PostgreSQL:

```sql
CREATE DATABASE student_management;
```

Connect to the database and create the students table:

```sql
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    age INTEGER NOT NULL CHECK (age > 18),
    email VARCHAR(150) UNIQUE NOT NULL
);
```

## 6. Environment Variables

Configure the following environment variables when running the application:

```text
DB_URL=jdbc:postgresql://localhost:5432/student_management
DB_USERNAME=postgres
DB_PASSWORD=your_password_here
```

Replace `your_password_here` with your PostgreSQL password.

Never commit your actual database password to GitHub.

## 7. Running the Application

1. Install Java and PostgreSQL.
2. Open the project in IntelliJ IDEA.
3. Configure the database and environment variables.
4. Reload the Maven project.
5. Run `StudentmanagementApplication.java`.
6. Open your browser and visit:

**Student Registration:**

`http://localhost:8080/student/register`

**View All Students:**

`http://localhost:8080/student/all`

**View Individual Student:**

`http://localhost:8080/student/{id}`

Replace `{id}` with the actual student ID.

## 8. Spring Framework Concepts Used

### Inversion of Control (IoC)

Spring manages the creation and lifecycle of application objects instead of requiring us to create and manage them manually.

### Beans

The controller, service, and repository are registered as Spring-managed beans using annotations such as:

- `@Controller`
- `@Service`
- `@Repository`

### Dependency Injection (DI)

The application uses constructor-based dependency injection to provide required objects to other classes.

For example, `StudentController` receives `StudentService` through its constructor, while `StudentService` receives `StudentRepository`.

### Handler Mapping

Spring MVC uses annotations to map HTTP requests to controller methods:

- `@GetMapping` handles GET requests.
- `@PostMapping` handles POST requests.
- `@RequestMapping` defines the common URL path.
- `@PathVariable` retrieves values from the URL.

## 9. Data Access

The application uses JDBC to communicate with PostgreSQL.

It uses:

- `DriverManager` to establish database connections.
- `PreparedStatement` to execute SQL statements safely.
- `ResultSet` to retrieve records.
- SQL INSERT statements to register students.
- SQL SELECT statements to retrieve student records.

## 10. Author

**TUYISHIME Kevine**

Student Registration and Management System – Academic Project