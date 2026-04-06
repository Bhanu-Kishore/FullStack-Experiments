package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main application class to demonstrate Annotation-based Spring IoC container interaction.
 */
public class MainAnnotation {
    public static void main(String[] args) {
        // Load the Spring container from Annotation-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the bean from the Spring IoC container
        Student student = (Student) context.getBean("studentAnnotationBean");

        // Print the injected values
        System.out.println("--- Spring Core: Annotation Configuration (Constructor & Setter Injection) ---");
        System.out.println(student.toString());

        // Closing context
        ((AnnotationConfigApplicationContext) context).close();
    }
}
