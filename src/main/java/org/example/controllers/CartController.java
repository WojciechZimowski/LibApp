package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.Book;
import org.example.models.Cart;
import org.example.models.User;
import org.example.services.BookServiceInterface;
import org.example.services.CartServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceInterface cartService;
    private final UserServiceInterface userService;
    private final BookServiceInterface bookService;
    public record AddToCartRequest(String bookId,int quantity){}

    @GetMapping
    public ResponseEntity<List<Cart>> getCart(Authentication authentication) {
        User user = userService.findUserByName(authentication.getName())
                .orElseThrow(()->new RuntimeException("User not found"));
        return ResponseEntity.ok(cartService.findCart(user.getId()));
    }
    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request, Authentication authentication) {
        User user  = userService.findUserByName(authentication.getName())
                .orElseThrow(()->new RuntimeException("User not found"));

        Book book = bookService.findBookById(request.bookId()).orElseThrow(()->new RuntimeException("Book not found"));
        if(book.getQuantity()< request.quantity()){
            throw new IllegalArgumentException("Quantity less than request quantity");
        }
        return ResponseEntity.ok(cartService.addToCart(user,book,request.quantity()));

    }
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.noContent().build();
    }
}
