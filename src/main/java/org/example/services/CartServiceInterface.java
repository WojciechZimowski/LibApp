package org.example.services;

import org.example.models.Book;
import org.example.models.Cart;
import org.example.models.User;

import java.util.List;

public interface CartServiceInterface {
    Cart addToCart(User user, Book book, int quantity);
    void removeFromCart(String id);
    List<Cart> findCart(String userId);
    void clearCart(String userId);
}
