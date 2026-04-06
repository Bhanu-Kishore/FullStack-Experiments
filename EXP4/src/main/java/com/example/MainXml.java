package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main application class to demonstrate XML-based Spring IoC container interaction.
 */
public class MainXml {
    public static void main(String[] args) {
        // Load the Spring container from XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the bean from the Spring IoC container
        Student student = (Student) context.getBean("studentBean");

        // Print the injected values
        System.out.println("--- Spring Core: XML Configuration (Constructor & Setter Injection) ---");
        System.out.println(student.toString());
        
        // Closing context is a good practice (not strictly required for Console apps)
        ((ClassPathXmlApplicationContext) context).close();
    }
}
