package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable int id) {
        if (id <= 0) {
            throw new InvalidInputException("Invalid ID provided: " + id + ". ID must be greater than zero.");
        }
        
        if (id == 101) {
            return new Student(101, "John Doe", "Computer Science");
        } else {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
    }
}
