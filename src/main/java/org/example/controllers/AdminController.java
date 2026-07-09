package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.Book;
import org.example.models.User;
import org.example.services.BookServiceInterface;
import org.example.services.OrderServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookServiceInterface bookService;
    private final OrderServiceInterface orderService;
    private final UserServiceInterface userService;

    public record StatusUpdateRequest(String status){}


    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id){
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable String orderId, @RequestBody StatusUpdateRequest request) {
        orderService.updateOrder(orderId, request.status());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
