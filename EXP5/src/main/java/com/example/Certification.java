package com.example;

import org.springframework.stereotype.Component;

@Component
public class Certification {
    private String id;
    private String name;
    private String dateOfCompletion;

    public Certification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(String dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    @Override
    public String toString() {
        return "Certification ID: " + id + "\n" +
               "Certification Name: " + name + "\n" +
               "Date Of Completion: " + dateOfCompletion;
    }
}
