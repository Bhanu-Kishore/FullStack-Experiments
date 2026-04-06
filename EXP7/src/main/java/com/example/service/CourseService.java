package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.model.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private List<Course> courseList = new ArrayList<>();

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public List<Course> getAllCourses() {
        return courseList;
    }

    public Course getCourseById(int id) {
        return courseList.stream()
                .filter(c -> c.getCourseId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean updateCourse(int id, Course updatedCourse) {
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseId() == id) {
                updatedCourse.setCourseId(id);
                courseList.set(i, updatedCourse);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(int id) {
        return courseList.removeIf(c -> c.getCourseId() == id);
    }

    public List<Course> searchByTitle(String title) {
        return courseList.stream()
                .filter(c -> c.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }
}
