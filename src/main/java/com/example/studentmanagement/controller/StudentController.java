
package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Display registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    // Register student
    @PostMapping("/register")
    public String registerStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            Model model) {

        // Check age
        if (student.getAge() != null && student.getAge() <= 18) {
            result.rejectValue(
                    "age",
                    "invalid.age",
                    "Age must be greater than 18"
            );
        }

        // Return form if validation fails
        if (result.hasErrors()) {
            return "register";
        }

        try {
            studentService.saveStudent(student);

            model.addAttribute(
                    "successMessage",
                    "Student registered successfully!"
            );

            model.addAttribute("student", new Student());

            return "register";

        } catch (RuntimeException e) {

            model.addAttribute(
                    "errorMessage",
                    "Registration failed. Please check whether the email already exists."
            );

            return "register";
        }
    }

    // Display all students
    @GetMapping("/all")
    public String showAllStudents(Model model) {

        model.addAttribute(
                "students",
                studentService.getAllStudents()
        );

        return "students";
    }

    // Display a specific student (Bonus)
    @GetMapping("/{id}")
    public String showStudentById(
            @PathVariable int id,
            Model model) {

        return studentService.getStudentById(id)
                .map(student -> {
                    model.addAttribute(
                            "students",
                            java.util.Collections.singletonList(student)
                    );

                    model.addAttribute("selectedStudent", student);

                    return "students";
                })
                .orElseGet(() -> {
                    model.addAttribute(
                            "students",
                            java.util.Collections.emptyList()
                    );

                    model.addAttribute(
                            "errorMessage",
                            "Student with ID " + id + " was not found."
                    );

                    return "students";
                });
    }
}
