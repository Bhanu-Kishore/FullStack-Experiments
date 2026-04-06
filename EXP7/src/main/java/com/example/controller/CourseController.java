package com.example.controller;

import java.util.List;
import com.example.model.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return new ResponseEntity<>("Course added successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable int id, @RequestBody Course course) {
        boolean isUpdated = courseService.updateCourse(id, course);
        if (isUpdated) {
            return new ResponseEntity<>("Course updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        boolean isDeleted = courseService.deleteCourse(id);
        if (isDeleted) {
            return new ResponseEntity<>("Course deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ResponseEntity<>("Search title cannot be empty", HttpStatus.BAD_REQUEST);
        }
        List<Course> results = courseService.searchByTitle(title);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
