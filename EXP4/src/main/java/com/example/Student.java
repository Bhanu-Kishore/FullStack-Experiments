package com.example;

/**
 * POJO class for Student demonstrating Constructor and Setter Injection.
 */
public class Student {
    private int studentId;
    private String name;
    private String course;
    private String year;

    // Constructor with ALL fields
    public Student(int studentId, String name, String course, String year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    // Constructor for ID and Name (to be used specifically for Constructor Injection)
    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    // Default Constructor (needed for Spring's setter injection or internal instantiation)
    public Student() {
    }

    // Getters for all fields
    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getYear() {
        return year;
    }

    // Setter methods for at least two fields (course, year)
    // As per requirements: "Create setter methods for at least two fields (course, year)"
    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(String year) {
        this.year = year;
    }

    // For constructor-injected fields, we can also have setters if needed for full flexibility
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\n" +
               "Name: " + name + "\n" +
               "Course: " + course + "\n" +
               "Year: " + year;
    }
}
