package com.example;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private List<Book> booksList = new ArrayList<>();

    public LibraryController() {
        // Sample books to pre-populate the list
        booksList.add(new Book(1, "Java Programming", "John Doe", 450.00));
        booksList.add(new Book(2, "Spring Boot Guide", "Jane Smith", 550.00));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Online Library";
    }

    @GetMapping("/count")
    public int count() {
        return 100;
    }

    @GetMapping("/price")
    public double price() {
        return 499.99;
    }

    @GetMapping("/books")
    public List<String> getBookTitles() {
        return Arrays.asList("Java Programming", "Spring Boot Guide", "Microservices Design", "Python for All");
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        return booksList.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam String title) {
        return "Search confirmation for book: " + title;
    }

    @GetMapping("/author/{name}")
    public String getAuthorInfo(@PathVariable String name) {
        return "Displaying books by author: " + name;
    }

    @PostMapping("/addbook")
    public String addBook(@RequestBody Book book) {
        booksList.add(book);
        return "Book added successfully: " + book.getTitle();
    }

    @GetMapping("/viewbooks")
    public List<Book> viewAllBooks() {
        return booksList;
    }
}
