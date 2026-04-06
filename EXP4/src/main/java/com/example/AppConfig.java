package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Annotation-based Configuration class for Spring IoC Container.
 */
@Configuration
public class AppConfig {

    @Bean(name = "studentAnnotationBean")
    public Student student() {
        // Constructor Injection for studentId and name
        Student student = new Student(101, "Bhanu");

        // Setter Injection for course and year
        student.setCourse("AI & DS");
        student.setYear("2nd Year");

        return student;
    }
}
