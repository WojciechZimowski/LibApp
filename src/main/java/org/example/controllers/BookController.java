package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.Book;
import org.example.services.BookServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceInterface bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book>getBookById(@PathVariable String id){
        return bookService.findBookById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
