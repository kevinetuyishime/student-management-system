
package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final String url = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:postgresql://localhost:5432/student_management"
    );

    private final String username = System.getenv().getOrDefault(
            "DB_USERNAME", "postgres"
    );

    private final String password = System.getenv("DB_PASSWORD");

    // Database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // INSERT student
    public void save(Student student) {

        String sql = "INSERT INTO students (full_name, age, email) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, student.getFullName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    // SELECT all students
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                String fullName = result.getString("full_name");
                String[] names = fullName.split("\\s+", 2);

                Student student = new Student(
                        names[0],
                        names.length > 1 ? names[1] : "",
                        result.getString("email"),
                        result.getInt("age")
                );

                student.setId(result.getInt("id"));

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving students", e);
        }

        return students;
    }

    // SELECT student by ID (Bonus)
    public Optional<Student> findById(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String fullName = result.getString("full_name");
                    String[] names = fullName.split("\\s+", 2);

                    Student student = new Student(
                            names[0],
                            names.length > 1 ? names[1] : "",
                            result.getString("email"),
                            result.getInt("age")
                    );

                    student.setId(result.getInt("id"));

                    return Optional.of(student);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding student", e);
        }

        return Optional.empty();
    }
}
