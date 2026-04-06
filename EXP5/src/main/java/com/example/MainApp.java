package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Student student = context.getBean(Student.class);
        
        // Setting values to match expected output
        student.setId(101);
        student.setName("Bhanu");
        student.setGender("Male");
        
        Certification cert = student.getCertification();
        cert.setId("C101");
        cert.setName("Spring Framework");
        cert.setDateOfCompletion("05-04-2026");

        System.out.println(student);

        context.close();
    }
}
